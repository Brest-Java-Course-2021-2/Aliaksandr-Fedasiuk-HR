package com.epam.brest.service;

import com.epam.brest.model.Department;

import java.util.List;

public interface DepartmentService {

    /**
     * Find all departments.
     *
     * @return departments list.
     */
    List<Department> findAll();

    /**
     * Find department by Id.
     *
     * @param departmentId department Id.
     * @return department
     */
    Department getDepartmentById(Integer departmentId);

    /**
     * Persist new department.
     *
     * @param department department.
     * @return persisted department id.
     */
    Integer create(Department department);

    /**
     * Update department.
     *
     * @param department department.
     * @return number of updated records in the database.
     */
    Integer update(Department department);

    /**
     * Delete department.
     *
     * @param departmentId department id.
     * @return number of updated records in the database.
     */
    Integer delete(Integer departmentId);

    /**
     * Count departments.
     *
     * @return quantity of the departments.
     */
    Integer count();
}
