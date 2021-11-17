package com.epam.brest.service;

import com.epam.brest.model.Department;

public interface DepartmentService {

    Department getDepartmentById(Integer departmentId);

    Integer create(Department department);

    Integer update(Department department);

    Integer delete(Integer departmentId);

    Integer count();
}
