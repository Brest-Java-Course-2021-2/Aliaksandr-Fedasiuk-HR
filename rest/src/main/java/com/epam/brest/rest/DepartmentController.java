package com.epam.brest.rest;

import com.epam.brest.dao.DepartmentDaoJDBCImpl;
import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepartmentController {

    private static final Logger logger = LogManager.getLogger(DepartmentDaoJDBCImpl.class);

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(value = "/departments/{id}")
    public final Department getDepartmentById(@PathVariable Integer id) {

        logger.debug("department()");
        return departmentService.getDepartmentById(id);
    }

    @PostMapping(path = "/departments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Integer> createDepartment(@RequestBody Department department) {

        logger.debug("createDepartment({})", department);
        Integer id = departmentService.create(department);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(value = "/departments", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> updateDepartment(@RequestBody Department department) {

        logger.debug("updateDepartment({})", department);
        int result = departmentService.update(department);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/departments/{id}", produces = {"application/json"})
    public ResponseEntity<Integer> deleteDepartment(@PathVariable Integer id) {

        int result = departmentService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
