package com.epam.brest.rest;

import com.epam.brest.model.Department;
import com.epam.brest.rest.exception.CustomExceptionHandler;
import com.epam.brest.service.DepartmentService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @Captor
    private ArgumentCaptor<Integer> captorId;

    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setControllerAdvice(new CustomExceptionHandler())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void getDepartmentById() throws Exception {
        Department department = new Department();
        department.setDepartmentId(45);
        department.setDepartmentName("name");

        Mockito.when(departmentService.getDepartmentById(anyInt())).thenReturn(department);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/departments/123")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.departmentId", Matchers.is(department.getDepartmentId())))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.departmentName", Matchers.is(department.getDepartmentName())))
        ;

        Mockito.verify(departmentService).getDepartmentById(captorId.capture());

        Integer id = captorId.getValue();
        Assertions.assertEquals(123, id);
    }

    @Test
    public void getDepartmentByIdException() throws Exception {

        Mockito.when(departmentService.getDepartmentById(anyInt()))
                .thenThrow(new IllegalArgumentException("test message"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/departments/123")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Handle: test message"))
        ;
    }
}
