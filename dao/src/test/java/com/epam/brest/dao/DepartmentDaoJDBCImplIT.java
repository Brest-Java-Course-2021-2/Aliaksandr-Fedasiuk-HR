package com.epam.brest.dao;

import com.epam.brest.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath*:test-jdbc-conf.xml"})
@Transactional
@Rollback
class DepartmentDaoJDBCImplIT {

    private final Logger logger = LogManager.getLogger(DepartmentDaoJDBCImplIT.class);

    private DepartmentDaoJDBCImpl departmentDaoJDBC;

    public DepartmentDaoJDBCImplIT(@Autowired DepartmentDao departmentDaoJDBC) {
        this.departmentDaoJDBC = (DepartmentDaoJDBCImpl) departmentDaoJDBC;
    }

    @Test
    void findAll() {
        logger.debug("Execute test: findAll()");
        assertNotNull(departmentDaoJDBC);
        assertNotNull(departmentDaoJDBC.findAll());
    }

    @Test
    void create() {
        assertNotNull(departmentDaoJDBC);
        int departmentsSizeBefore = departmentDaoJDBC.count();
        Department department = new Department("HR");
        Integer newDepartmentId = departmentDaoJDBC.create(department);
        assertNotNull(newDepartmentId);
        assertEquals((int) departmentsSizeBefore, departmentDaoJDBC.count() - 1);
    }

    @Test
    void tryToCreateEqualsDepartments() {
        assertNotNull(departmentDaoJDBC);
        Department department = new Department("HR");

        assertThrows(IllegalArgumentException.class, () -> {
            departmentDaoJDBC.create(department);
            departmentDaoJDBC.create(department);
        });
    }

    @Test
    void shouldCount() {
        assertNotNull(departmentDaoJDBC);
        Integer quantity = departmentDaoJDBC.count();
        assertNotNull(quantity);
        assertTrue(quantity > 0);
        assertEquals(Integer.valueOf(3), quantity);
    }
}
