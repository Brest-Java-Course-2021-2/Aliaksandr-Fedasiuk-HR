package com.epam.brest.dao;

import com.epam.brest.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DepartmentDaoJDBCImpl implements DepartmentDao {

    private final Logger logger = LoggerFactory.getLogger(DepartmentDaoJDBCImpl.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Value("${SQL_DEPARTMENTS_COUNT}")
    public String sqlDepartmentCount;

    @Value("${SQL_ALL_DEPARTMENTS}")
    private String sqlGetAllDepartments;

    @Value("${SQL_DEPARTMENT_BY_ID}")
    private String sqlGetDepartmentById;

    @Value("${SQL_CHECK_UNIQUE_DEPARTMENT_NAME}")
    private String sqlCheckUniqueDepartmentName;

    @Value("${SQL_CREATE_DEPARTMENT}")
    private String sqlCreateDepartment;

    @Value("${SQL_UPDATE_DEPARTMENT_NAME}")
    private String sqlUpdateDepartmentName;

    @Value("${SQL_DELETE_DEPARTMENT_BY_ID}")
    private String sqlDeleteDepartmentById;

    public DepartmentDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        logger.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(sqlGetAllDepartments, new DepartmentRowMapper());
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        logger.debug("Get department by id = {}", departmentId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentId", departmentId);
        return namedParameterJdbcTemplate.queryForObject(sqlGetDepartmentById, sqlParameterSource, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {
        logger.debug("Create department: {}", department);

        if (!isDepartmentUnique(department.getDepartmentName())) {
            logger.warn("Department with the same name {} already exists.", department.getDepartmentName());
            throw new IllegalArgumentException("Department with the same name already exists in DB.");
        }

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentName", department.getDepartmentName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCreateDepartment, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    private boolean isDepartmentUnique(String departmentName) {
        logger.debug("Check DepartmentName: {} on unique", departmentName);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource("departmentName", departmentName);
        return namedParameterJdbcTemplate.queryForObject(sqlCheckUniqueDepartmentName, sqlParameterSource, Integer.class) == 0;
    }

    @Override
    public Integer update(Department department) {
        logger.debug("Update department: {}", department);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentName", department.getDepartmentName())
                        .addValue("departmentId", department.getDepartmentId());
        return namedParameterJdbcTemplate.update(sqlUpdateDepartmentName, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer departmentId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentId", departmentId);
        return namedParameterJdbcTemplate.update(sqlDeleteDepartmentById, sqlParameterSource);
    }

    @Override
    public Integer count() {
        logger.debug("count()");
        return namedParameterJdbcTemplate
                .queryForObject(sqlDepartmentCount, new MapSqlParameterSource(), Integer.class);
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
