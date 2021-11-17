package com.epam.brest.web_app;

import com.epam.brest.model.Department;
import com.epam.brest.service.DepartmentDtoService;
import com.epam.brest.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DepartmentController {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentDtoService departmentDtoService,
                                DepartmentService departmentService) {
        this.departmentDtoService = departmentDtoService;
        this.departmentService = departmentService;
    }

    /**
     * Goto departments list page.
     *
     * @return view name
     */
    @GetMapping(value = "/departments")
    public final String departments(Model model) {
        model.addAttribute("departments", departmentDtoService.findAllWithAvgSalary());
        return "departments";
    }

    /**
     * Goto edit department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department/{id}")
    public final String gotoEditDepartmentPage(@PathVariable Integer id, Model model) {
        logger.debug("gotoEditDepartmentPage(id:{},model:{})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("department", departmentService.getDepartmentById(id));
        return "department";
    }

    /**
     * Goto new department page.
     *
     * @return view name
     */
    @GetMapping(value = "/department")
    public final String gotoAddDepartmentPage(Model model) {
        logger.debug("gotoAddDepartmentPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }

    /**
     * Persist new department into persistence storage.
     *
     * @param department new department with filled data.
     * @return view name
     */
    @PostMapping(value = "/department")
    public String addDepartment(Department department) {

        logger.debug("addDepartment({}, {})", department);
        this.departmentService.create(department);
        return "redirect:/departments";
    }

    /**
     * Update department.
     *
     * @param department department with filled data.
     * @return view name
     */
    @PostMapping(value = "/department/{id}")
    public String updateDepartment(Department department) {

        logger.debug("updateDepartment({}, {})", department);
        this.departmentService.update(department);
        return "redirect:/departments";
    }

    /**
     * Delete department.
     *
     * @return view name
     */
    @GetMapping(value = "/department/{id}/delete")
    public final String deleteDepartmentById(@PathVariable Integer id, Model model) {

        logger.debug("delete({},{})", id, model);
        departmentService.delete(id);
        return "redirect:/departments";
    }
}
