package com.samhcoco.managementsystem.employee.controller;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeValidator employeeValidator; // todo - remove

    @Autowired
    public EmployeeController(EmployeeValidator employeeValidator) {
        this.employeeValidator = employeeValidator;
    }

    // todo - remove
    @GetMapping("api/v1/employee")
    public ResponseEntity<Object> testValidation() {
        employeeValidator.validateCreate(new Employee());
        return ResponseEntity.ok().build();
    }

}
