package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DepartmentServiceRest implements DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentServiceRest() {
    }

    public DepartmentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Department> findAll() {
        logger.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Department>) responseEntity.getBody();
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        logger.debug("findById({})", departmentId);
        ResponseEntity<Department> responseEntity =
                restTemplate.getForEntity(url + "/" + departmentId, Department.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Department department) {
        logger.debug("create({})", department);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, department, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Department department) {

        logger.debug("update({})", department);
        // restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(department, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer departmentId) {
        logger.debug("delete({})", departmentId);
        //restTemplate.delete(url + "/" + departmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(url + "/" + departmentId, HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(url + "/count", Integer.class);
        return responseEntity.getBody();
    }
}
