package com.epam.brest.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class DepartmentControllerTest {

    @Autowired
    private DepartmentController departmentController;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    //MockMvcDepartmentService departmentService = new MockMvcDepartmentService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
//                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void getDepartmentById() {
    }
}
