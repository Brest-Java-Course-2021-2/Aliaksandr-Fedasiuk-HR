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

    public static final String SELECT_COUNT_FROM_DEPARTMENT = "select count(*) from department";

    private final String SQL_ALL_DEPARTMENTS =
            "select d.department_id, d.department_name from department d order by d.department_name";

    private final String SQL_DEPARTMENT_BY_ID = "select d.department_id, d.department_name from department d " +
            " where department_id = :departmentId";

    private final String SQL_CHECK_UNIQUE_DEPARTMENT_NAME = "select count(d.department_name) " +
            "from department d where lower(d.department_name) = lower(:departmentName)";

    private final String SQL_CREATE_DEPARTMENT =
            "insert into department(department_name) values(:departmentName)";

    private final String SQL_UPDATE_DEPARTMENT_NAME = "update department set department_name = :departmentName " +
            "where department_id = :departmentId";

    private final String SQL_DELETE_DEPARTMENT_BY_ID = "delete from department where department_id = :departmentId";

    @Deprecated
    public DepartmentDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public DepartmentDaoJDBCImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        LOGGER.debug("Start: findAll()");
        return namedParameterJdbcTemplate.query(SQL_ALL_DEPARTMENTS, new DepartmentRowMapper());
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        LOGGER.debug("Get department by id = {}", departmentId);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentId", departmentId);
        return namedParameterJdbcTemplate.queryForObject(SQL_DEPARTMENT_BY_ID, sqlParameterSource, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {
        LOGGER.debug("Create department: {}", department);

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
        LOGGER.debug("Update department: {}", department);
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentName", department.getDepartmentName())
                        .addValue("departmentId", department.getDepartmentId());
        return namedParameterJdbcTemplate.update(SQL_UPDATE_DEPARTMENT_NAME, sqlParameterSource);
    }

    @Override
    public Integer delete(Integer departmentId) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentId", departmentId);
        return namedParameterJdbcTemplate.update(SQL_DELETE_DEPARTMENT_BY_ID, sqlParameterSource);
    }

    @Override
    public Integer count() {
        LOGGER.debug("count()");
        return namedParameterJdbcTemplate
                .queryForObject(SELECT_COUNT_FROM_DEPARTMENT, new MapSqlParameterSource(), Integer.class);
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
