package com.epam.brest.dao;

import com.epam.brest.model.dto.DepartmentDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  Department DTO DAO implementation.
 */
@Component
public class DepartmentDtoDaoJdbc implements DepartmentDtoDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String findAllWithAvgSalarySql = "SELECT\n" +
            "\td.department_id AS departmentId,\n" +
            "\td.department_name AS departmentName,\n" +
            "\tavg(e.salary) AS avgSalary\n" +
            "FROM\n" +
            "\tdepartment d\n" +
            "LEFT JOIN employee e ON\n" +
            "\td.department_id = e.department_id\n" +
            "GROUP BY\n" +
            "\td.department_id,\n" +
            "\td.department_name\n" +
            "ORDER BY\n" +
            "\tdepartment_name";


    public DepartmentDtoDaoJdbc(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {

        List<DepartmentDto> departments = namedParameterJdbcTemplate.query(findAllWithAvgSalarySql,
                BeanPropertyRowMapper.newInstance(DepartmentDto.class));
        return departments;
    }

}
