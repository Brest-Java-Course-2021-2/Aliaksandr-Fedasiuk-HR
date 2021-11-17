package com.epam.brest.service.impl;

import com.epam.brest.dao.DepartmentDao;
import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    private final DepartmentDao departmentDao;

    public DepartmentServiceImpl(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Override
    public Department getDepartmentById(Integer departmentId) {
        logger.debug("Get department by id = {}", departmentId);
        return this.departmentDao.getDepartmentById(departmentId);
    }

    @Override
    @Transactional
    public Integer create(Department department) {
        logger.debug("create({})", department);
        return this.departmentDao.create(department);
    }

    @Override
    public Integer update(Department department) {
        logger.debug("update({})", department);
        return this.departmentDao.update(department);
    }

    @Override
    public Integer delete(Integer departmentId) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Integer count() {
        logger.debug("count()");
        return this.departmentDao.count();
    }
}
