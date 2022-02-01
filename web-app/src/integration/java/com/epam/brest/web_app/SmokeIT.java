package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * By default, @SpringBootTest will not start a server.
 * Read please https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SmokeIT {

    private final Logger logger = LoggerFactory.getLogger(SmokeIT.class);

    @Autowired
    private MockMvc mockMvc;

    /**
     * cause rest-server is not up and running
     * rest-client should be tested via MockRestServer
     */
    @MockBean
    private DepartmentDtoService restDepartmentService;

    // TODO: DepartmentDtoService vs DepartmentService
    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ApplicationContext context;

    private WebClient webClient;

    @BeforeEach
    void setup(final WebApplicationContext webContext) {
        webClient = MockMvcWebClientBuilder
                .webAppContextSetup(webContext)
                .build();
        // TODO: why do we need to set false
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Test
    void contextLoads() {
        // just to show how you can look at the current spring context
        assertNotNull(context);
        logger.info("The Spring context has been loaded successfully");
    }

    /**
     * https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-server-htmlunit-mah
     *
     * @throws Exception an error
     */
    @Test
    void openHomeAndClickDepartment() throws Exception {
        emulateRestServer();

        // 1. Home page
        final HtmlPage page = webClient.getPage("http://localhost/");
        final HtmlAnchor link = (HtmlAnchor) page.getByXPath("//a[@href='/department/123']").get(0);

        // 2. Click on the department
        final HtmlPage detailsPage =  link.click();
        final HtmlTextInput departmentName = (HtmlTextInput) detailsPage.getElementById("field_departmentName");

        assertEquals("test-department", departmentName.getText());
    }

    /**
     * Note that we're not gonna up web-app server cause we have to run rest-server too (that's not trivial with maven).
     */
    private void emulateRestServer() {
        // to emulate rest-server
        final DepartmentDto testDepartment = new DepartmentDto();
        testDepartment.setDepartmentId(123);

        final Department department = new Department();
        department.setDepartmentName("test-department");

        when(restDepartmentService.findAllWithAvgSalary()).thenReturn(Collections.singletonList(testDepartment));
        when(departmentService.getDepartmentById(123)).thenReturn(department);
    }
}
