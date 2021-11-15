package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
@Transactional
class DepartmentControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private DepartmentService departmentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnDepartmentsPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/departments")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(1)),
                                hasProperty("departmentName", is("IT")),
                                hasProperty("avgSalary", is(BigDecimal.valueOf(150)))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(2)),
                                hasProperty("departmentName", is("SECURITY")),
                                hasProperty("avgSalary", is(BigDecimal.valueOf(400)))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("departmentId", is(3)),
                                hasProperty("departmentName", is("MANAGEMENT")),
                                hasProperty("avgSalary", isEmptyOrNullString())
                        )
                )));
    }


    @Test
    void shouldAddDepartment() throws Exception {
        // WHEN
        assertNotNull(departmentService);
        Integer departmentsSizeBefore = departmentService.count();
        assertNotNull(departmentsSizeBefore);
        Department department = new Department("HR");

        // THEN
        //Integer newDepartmentId = departmentService.create(department);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentName", department.getDepartmentName())
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));


        // VERIFY
        assertEquals(departmentsSizeBefore, departmentService.count() - 1);
    }
}
