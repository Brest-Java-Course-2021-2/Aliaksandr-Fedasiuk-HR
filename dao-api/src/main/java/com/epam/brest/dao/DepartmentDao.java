package com.epam.brest.dao;

import com.epam.brest.model.Department;

import java.util.List;

public interface DepartmentDao {

    List<Department> findAll();

    Integer create(Department department);

    Integer update(Department department);

    Integer delete(Integer departmentId);

}
