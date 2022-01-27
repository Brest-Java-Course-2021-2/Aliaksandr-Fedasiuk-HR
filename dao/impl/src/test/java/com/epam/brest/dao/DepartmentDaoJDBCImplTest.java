package com.epam.brest.dao;

import com.epam.brest.model.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class DepartmentDaoJDBCImplTest {

    @InjectMocks
    private DepartmentDaoJDBCImpl departmentDaoJDBC;

    @Mock
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Captor
    private ArgumentCaptor<RowMapper<Department>> captorMapper;

    @Captor
    private ArgumentCaptor<SqlParameterSource> captorSource;

    @AfterEach
    public void check() {
        Mockito.verifyNoMoreInteractions(namedParameterJdbcTemplate);
    }

    @Test
    public void findAll() {
        String sql = "select";
        ReflectionTestUtils.setField(departmentDaoJDBC, "sqlGetAllDepartments", sql);
        Department department = new Department();
        List<Department> list = Collections.singletonList(department);

        Mockito.when(namedParameterJdbcTemplate.query(any(), ArgumentMatchers.<RowMapper<Department>>any()))
                .thenReturn(list);

        List<Department> result = departmentDaoJDBC.findAll();

        Mockito.verify(namedParameterJdbcTemplate).query(eq(sql), captorMapper.capture());

        RowMapper<Department> mapper = captorMapper.getValue();

        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertSame(department, result.get(0));
    }

    @Test
    public void getDepartmentById() {
        String sql = "get";
        ReflectionTestUtils.setField(departmentDaoJDBC, "sqlGetDepartmentById", sql);
        int id = 0;
        Department department = new Department();

        Mockito.when(namedParameterJdbcTemplate.queryForObject(
                any(),
                ArgumentMatchers.<SqlParameterSource>any(),
                ArgumentMatchers.<RowMapper<Department>>any())
        ).thenReturn(department);

        Department result = departmentDaoJDBC.getDepartmentById(id);

        Mockito.verify(namedParameterJdbcTemplate)
                .queryForObject(eq(sql), captorSource.capture(), captorMapper.capture());

        SqlParameterSource source = captorSource.getValue();
        RowMapper<Department> mapper = captorMapper.getValue();

        Assertions.assertNotNull(source);
        Assertions.assertNotNull(mapper);

        Assertions.assertNotNull(result);
        Assertions.assertSame(department, result);
    }
}
