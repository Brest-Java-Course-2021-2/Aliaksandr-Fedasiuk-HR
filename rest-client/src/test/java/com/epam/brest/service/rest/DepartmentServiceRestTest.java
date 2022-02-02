package com.epam.brest.service.rest;

import com.epam.brest.model.Department;
import com.epam.brest.service.config.RestClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static com.epam.brest.model.constants.DepartmentConstants.DEPARTMENT_NAME_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({RestClientConfig.class, DepartmentServiceRest.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepartmentServiceRestTest {

    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceRestTest.class);

    @Value("${rest.server}")
    private String restServer;

    private MockRestServiceServer mockServer;

    @Autowired
    private DepartmentServiceRest departmentService;

    @BeforeEach
    public void before() {
        // we have to use the same RestTemplate
        final RestTemplate restTemplate = (RestTemplate) ReflectionTestUtils.getField(departmentService, "restTemplate");
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {
        logger.debug("shouldFindAllDepartments()");

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments", restServer))))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("[{\"departmentId\":0,\"departmentName\":\"d0\"}," +
                                "{\"departmentId\":1,\"departmentName\":\"d1\"}]")
                );

        // when
        final List<Department> departments = departmentService.findAll();

        // then
        mockServer.verify();
        assertEquals("[{departmentId=0, departmentName=d0}, {departmentId=1, departmentName=d1}]",
                departments.toString());
    }

    @Test
    public void shouldCreateDepartment() throws Exception {
        logger.debug("shouldCreateDepartment()");

        // given
        final Department department = new Department()
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments", restServer))))
                .andExpect(method(HttpMethod.POST))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        // when
        final Integer id = departmentService.create(department);

        // then
        mockServer.verify();
        assertEquals(1, id);
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {
        // given
        final String name = RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE);
        final Department department = new Department()
                .setDepartmentId(1)
                .setDepartmentName(name);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments/1", restServer))))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(String.format("{\"departmentId\":1,\"departmentName\":\"%s\"}", name))
                );

        // when
        final Department resultDepartment = departmentService.getDepartmentById(1);

        // then
        mockServer.verify();
        assertNotNull(resultDepartment);
        assertEquals(1, resultDepartment.getDepartmentId());
        assertEquals(name, resultDepartment.getDepartmentName());
    }

    @Test
    public void shouldUpdateDepartment() throws Exception {

        // given
        final Department department = new Department()
                .setDepartmentId(1)
                .setDepartmentName(RandomStringUtils.randomAlphabetic(DEPARTMENT_NAME_SIZE));

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments", restServer))))
                .andExpect(method(HttpMethod.PUT))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments/1", restServer))))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(department))
                );

        // when
        final int result = departmentService.update(department);
        final Department updatedDepartment = departmentService.getDepartmentById(1);

        // then
        mockServer.verify();
        assertEquals(1, result);

        assertNotNull(updatedDepartment);
        assertEquals(1, updatedDepartment.getDepartmentId());
        assertEquals(updatedDepartment.getDepartmentName(), department.getDepartmentName());
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/departments/1", restServer))))
                .andExpect(method(HttpMethod.DELETE))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        // when
        int result = departmentService.delete(1);

        // then
        mockServer.verify();
        assertEquals(1, result);
    }
}
