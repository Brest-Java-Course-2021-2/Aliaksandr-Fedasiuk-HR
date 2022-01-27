package com.epam.brest.service.config;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.dao.DepartmentDaoJDBCImpl;
import com.epam.brest.dao.DepartmentDtoDao;
import com.epam.brest.dao.DepartmentDtoDaoJdbc;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.impl.DepartmentDtoServiceImpl;
import com.epam.brest.service.impl.DepartmentServiceImpl;
import com.epam.brest.testdb.SpringJdbcConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfig extends SpringJdbcConfig {

    @Bean
    DepartmentDtoDao departmentDtoDao() {
        return new DepartmentDtoDaoJdbc(namedParameterJdbcTemplate());
    }

    @Bean
    DepartmentDtoService departmentDtoService() {
        return new DepartmentDtoServiceImpl(departmentDtoDao());
    }

    @Bean
    DepartmentDao departmentDao() {
        return new DepartmentDaoJDBCImpl(namedParameterJdbcTemplate());
    }

    @Bean
    DepartmentService departmentService() {
        return new DepartmentServiceImpl(departmentDao());
    }
}
