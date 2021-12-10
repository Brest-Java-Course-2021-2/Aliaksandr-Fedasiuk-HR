package com.epam.brest.service.rest;

import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentDtoServiceRest implements DepartmentDtoService {

    private final Logger logger = LogManager.getLogger(DepartmentDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentDtoServiceRest() {
        // empty default constructor
    }

    public DepartmentDtoServiceRest(String url, RestTemplate restTemplate) {
        this();
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {
        logger.debug("findAllWithAvgSalary()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<DepartmentDto>) responseEntity.getBody();
    }
}
