package com.epam.brest.dao;

import com.epam.brest.model.dto.DepartmentDto;

import java.util.List;

/**
 * DepartmentDto DAO Interface.
 */
public interface DepartmentDtoDao {

    /**
     * Get all departments with avg salary by department.
     *
     * @return departments list.
     */
    List<DepartmentDto> findAllWithAvgSalary();

}
