package com.epam.brest.dao;

import com.epam.brest.model.Department;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class DepartmentDaoJDBCImpl implements DepartmentDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DepartmentDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Integer create(Department department) {
        return null;
    }

    @Override
    public Integer update(Department department) {
        return null;
    }

    @Override
    public Integer delete(Integer departmentId) {
        return null;
    }

}
