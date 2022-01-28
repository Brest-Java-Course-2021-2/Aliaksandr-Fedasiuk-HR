package com.epam.brest.service.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class DepartmentNotFoundException extends EmptyResultDataAccessException {
    public DepartmentNotFoundException(Integer id) {
        super("Department not found for id: " + id, 1);
    }
}
