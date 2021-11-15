package com.epam.brest.service;

import com.epam.brest.model.Department;

public interface DepartmentService {

    Integer create(Department department);

    Integer count();
}
