package com.epam.brest.service.impl;

import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:service-context-test.xml"})
@Transactional
class DepartmentDtoServiceImplIT {

    @Autowired
    DepartmentDtoService departmentDtoService;

    @Test
    public void shouldFindAllWithAvgSalary() {
        List<DepartmentDto> departments = departmentDtoService.findAllWithAvgSalary();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
        assertTrue(departments.get(0).getAvgSalary().intValue() > 0);
    }
}
