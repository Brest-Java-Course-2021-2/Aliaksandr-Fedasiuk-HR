package com.epam.brest.service.impl;

import com.epam.brest.dao.DepartmentDtoDao;
import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentDtoServiceImpl implements DepartmentDtoService {

    private final DepartmentDtoDao departmentDtoDao;

    public DepartmentDtoServiceImpl(DepartmentDtoDao departmentDtoDao) {
        this.departmentDtoDao = departmentDtoDao;
    }

    @Override
    public List<DepartmentDto> findAllWithAvgSalary() {
        return departmentDtoDao.findAllWithAvgSalary();
    }
}
