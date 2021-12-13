package com.epam.brest.rest;

import com.epam.brest.dao.DepartmentDaoJDBCImpl;
import com.epam.brest.model.dto.DepartmentDto;
import com.epam.brest.service.DepartmentDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@CrossOrigin
public class DepartmentDtoController {

    private static final Logger logger = LogManager.getLogger(DepartmentDaoJDBCImpl.class);

    private final DepartmentDtoService departmentDtoService;

    public DepartmentDtoController(DepartmentDtoService departmentDtoService) {
        this.departmentDtoService = departmentDtoService;
    }

    /**
     * Get department Dtos.
     *
     * @return Department Dtos collection.
     */
    @GetMapping(value = "/department-dtos")
    public final Collection<DepartmentDto> departmentDtos() {

        logger.debug("departmentDtos()");
        return departmentDtoService.findAllWithAvgSalary();
    }
}
