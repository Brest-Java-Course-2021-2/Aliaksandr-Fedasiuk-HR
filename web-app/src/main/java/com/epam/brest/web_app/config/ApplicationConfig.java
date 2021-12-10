package com.epam.brest.web_app.config;

import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.rest.DepartmentDtoServiceRest;
import com.epam.brest.service.rest.DepartmentServiceRest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan
public class ApplicationConfig {

    @Value("${rest.server.protocol}")
    private String protocol;
    @Value("${rest.server.host}")
    private String host;
    @Value("${rest.server.port}")
    private Integer port;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate(new SimpleClientHttpRequestFactory());
    }

    @Bean
    DepartmentDtoService departmentDtoService() {
        String url = String.format("%s://%s:%d/department-dtos", protocol, host, port);
        return new DepartmentDtoServiceRest(url, restTemplate());
    }

    ;

    @Bean
    DepartmentService departmentService() {
        String url = String.format("%s://%s:%d/departments", protocol, host, port);
        return new DepartmentServiceRest(url, restTemplate());
    }
}
