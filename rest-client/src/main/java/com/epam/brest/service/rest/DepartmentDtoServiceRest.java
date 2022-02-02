package com.epam.brest.service.rest;

import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepartmentDtoServiceRest implements DepartmentDtoService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDtoServiceRest.class);

    private RestTemplate restTemplate;

    public DepartmentDtoServiceRest(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {
        logger.debug("findAllWithAvgSalary()");
        ResponseEntity responseEntity = restTemplate.getForEntity("/department-dtos", List.class);
        return (List<DepartmentDto>) responseEntity.getBody();
    }
}
