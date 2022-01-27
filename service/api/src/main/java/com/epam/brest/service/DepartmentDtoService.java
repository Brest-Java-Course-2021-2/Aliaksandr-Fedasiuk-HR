package com.epam.brest.service;

import com.epam.brest.model.dto.DepartmentDto;

import java.util.List;

public interface DepartmentDtoService {

    /**
     * Get list of department Dto.
     *
     * @return list of department Dto.
     */
    List<DepartmentDto> findAllWithAvgSalary();
}
