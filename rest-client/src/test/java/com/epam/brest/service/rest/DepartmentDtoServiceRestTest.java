package com.epam.brest.service.rest;

import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.config.RestClientConfig;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@Import({RestClientConfig.class, DepartmentDtoServiceRest.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class DepartmentDtoServiceRestTest {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDtoServiceRestTest.class);

    @Value("${rest.server}")
    private String restServer;

    private MockRestServiceServer mockServer;

    @Autowired
    private DepartmentDtoServiceRest departmentDtoService;

    @BeforeEach
    public void before() {
        // we have to use the same RestTemplate
        final RestTemplate restTemplate = (RestTemplate) ReflectionTestUtils.getField(departmentDtoService, "restTemplate");
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldFindAllWithAvgSalary() throws Exception {

        logger.debug("shouldFindAllDepartments()");

        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(String.format("%s/department-dtos", restServer))))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header(HttpHeaders.ACCEPT, "application/json, application/*+json"))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new StringBuilder()
                                .append("[")
                                .append("{\"departmentId\":0,\"departmentName\":\"d0\",\"avgSalary\":100},")
                                .append("{\"departmentId\":1,\"departmentName\":\"d1\",\"avgSalary\":101}")
                                .append("]").toString())
                );

        // when
        final List<DepartmentDto> list = departmentDtoService.findAllWithAvgSalary();

        // then
        mockServer.verify();
        assertEquals(new StringBuilder()
                .append("[{departmentId=0, departmentName=d0, avgSalary=100}, ")
                .append("{departmentId=1, departmentName=d1, avgSalary=101}]").toString(), list.toString());

    }
}
