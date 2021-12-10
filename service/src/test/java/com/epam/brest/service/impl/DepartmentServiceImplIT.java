package com.epam.brest.service.impl;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import com.epam.brest.service.config.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@Import({ServiceTestConfig.class})
@PropertySource({"classpath:dao.properties"})
@Transactional
class DepartmentServiceImplIT {

    @Autowired
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCount() {
        assertNotNull(departmentService);
        Integer quantity = departmentService.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }

    @Test
    void create() {
        assertNotNull(departmentService);
        Integer departmentsSizeBefore = departmentService.count();
        assertNotNull(departmentsSizeBefore);
        Department department = new Department("HR");
        Integer newDepartmentId = departmentService.create(department);
        assertNotNull(newDepartmentId);
        assertEquals(departmentsSizeBefore, departmentService.count() - 1);
    }

    @Test
    void tryToCreateEqualsDepartments() {
        assertNotNull(departmentService);
        Department department = new Department("HR");

        assertThrows(IllegalArgumentException.class, () -> {
            departmentService.create(department);
            departmentService.create(department);
        });
    }
}
