package com.epam.brest.dao;

import com.epam.brest.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoJDBCImpl implements DepartmentDao {

    private final Logger LOGGER = LogManager.getLogger(DepartmentDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_DEPARTMENTS="select d.department_id, d.department_name from department d order by d.department_name";

    private final String SQL_CHECK_UNIQUE_DEPARTMENT_NAME="select count(d.department_name) " +
            "from department d where lower(d.department_name) = lower(:departmentName)";

    private final String SQL_CREATE_DEPARTMENT="insert into department(department_name) values(:departmentName)";

    public DepartmentDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Department> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_DEPARTMENTS, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("Start: create({})", department);

        if (!isDepartmentUnique(department.getDepartmentName())) {
            LOGGER.warn("Department with the same name {} already exists.", department.getDepartmentName());
            throw new IllegalArgumentException("Department with the same name already exists in DB.");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentName", department.getDepartmentName().toUpperCase());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_DEPARTMENT, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isDepartmentUnique(String departmentName) {
        LOGGER.debug("Check DepartmentName: {} on unique", departmentName);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("departmentName", departmentName);
        return namedParameterJdbcTemplate.queryForObject(SQL_CHECK_UNIQUE_DEPARTMENT_NAME, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Department department) {
        return null;
    }

    @Override
    public Integer delete(Integer departmentId) {
        return null;
    }

    private class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setDepartmentId(resultSet.getInt("department_id"));
            department.setDepartmentName(resultSet.getString("department_name"));
            return department;
        }
    }

}
