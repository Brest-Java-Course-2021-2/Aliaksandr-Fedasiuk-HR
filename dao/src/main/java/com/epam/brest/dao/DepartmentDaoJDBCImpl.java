package com.epam.brest.dao;

import com.epam.brest.model.Department;
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

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String SQL_ALL_DEPARTMENTS="select d.departmentId, d.departmentName from department d order by d.departmentName";
    private final String SQL_CREATE_DEPARTMENT="insert into department(departmentName) values(:departmentName)";

    public DepartmentDaoJDBCImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Department> findAll() {
        return namedParameterJdbcTemplate.query(SQL_ALL_DEPARTMENTS, new DepartmentRowMapper());
    }

    @Override
    public Integer create(Department department) {

        //TODO: isDepartmentUnique throw new IllegalArgumentException

        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource("departmentName", department.getDepartmentName().toUpperCase());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SQL_CREATE_DEPARTMENT, sqlParameterSource, keyHolder);
        return (Integer) keyHolder.getKey();
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
            department.setDepartmentId(resultSet.getInt("departmentId"));
            department.setDepartmentName(resultSet.getString("departmentName"));
            return department;
        }
    }

}
