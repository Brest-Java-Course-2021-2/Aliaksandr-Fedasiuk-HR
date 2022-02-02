package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentServiceRest implements DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDtoServiceRest.class);

    private RestTemplate restTemplate;

    public DepartmentServiceRest(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Department> findAll() {
        logger.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/departments", List.class);
        return (List<Department>) responseEntity.getBody();
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        logger.debug("findById({})", departmentId);
        ResponseEntity<Department> responseEntity =
                restTemplate.getForEntity(String.format("/departments/%d", departmentId), Department.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer create(Department department) {
        logger.debug("create({})", department);
        ResponseEntity responseEntity = restTemplate.postForEntity("/departments", department, Integer.class);
        return (Integer) responseEntity.getBody();
    }

    @Override
    public Integer update(Department department) {

        logger.debug("update({})", department);
        // restTemplate.put(url, department);

        HttpEntity<Department> entity = new HttpEntity<>(department);
        ResponseEntity<Integer> result = restTemplate.exchange("/departments", HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer delete(Integer departmentId) {
        logger.debug("delete({})", departmentId);
        //restTemplate.delete(url + "/" + departmentId);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Department> entity = new HttpEntity<>(headers);
        ResponseEntity<Integer> result =
                restTemplate.exchange(String.format("/departments/%d", departmentId), HttpMethod.DELETE, entity, Integer.class);
        return result.getBody();
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity("/count", Integer.class);
        return responseEntity.getBody();
    }
}
