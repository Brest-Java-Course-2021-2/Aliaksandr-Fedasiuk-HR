package com.epam.brest.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EmployeeController {

    /**
     * Goto employees list page.
     *
     * @return view name
     */
    @GetMapping(value = "/employees")
    public final String employees(Model model) {
        return "employees";
    }

    /**
     * Goto edit employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/employee/{id}")
    public final String gotoEditEmployeePage(@PathVariable Integer id, Model model) {
        return "employee";
    }

    /**
     * Goto new employee page.
     *
     * @return view name
     */
    @GetMapping(value = "/employee/add")
    public final String gotoAddEmployeePage(Model model) {
        return "employee";
    }
}
