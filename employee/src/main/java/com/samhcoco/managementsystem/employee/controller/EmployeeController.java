package com.samhcoco.managementsystem.employee.controller;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api")
public class EmployeeController {

    private static final String VERSION_1 = "v1";
    private static final String EMPLOYEE = "employee";

    private final EmployeeService employeeService;
    private final EmployeeEntityValidator employeeEntityValidator;

    @Autowired
    public EmployeeController(EmployeeService employeeService,
                              EmployeeEntityValidator employeeEntityValidator) {
        this.employeeService = employeeService;
        this.employeeEntityValidator = employeeEntityValidator;
    }

    @PostMapping(VERSION_1 + "/" + EMPLOYEE)
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        Map<String, String> errors = employeeEntityValidator.validateCreate(employee);
        if (errors.isEmpty()) {
            return ResponseEntity.status(CREATED)
                                 .body(employeeService.create(employee));
        }
        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }

    @PutMapping(VERSION_1 + "/" + EMPLOYEE)
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
        Map<String, String> errors = employeeEntityValidator.validateUpdate(employee);
        if (errors.isEmpty()) {
            return ResponseEntity.status(OK)
                                 .body(employeeService.create(employee));
        }
        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }

    @GetMapping(VERSION_1 + "/" + EMPLOYEE + "/list-all")
    public ResponseEntity<Object> listAllEmployees(com.samhcoco.managementsystem.core.model.Page page) {
        Page<Employee> pageResult = employeeService.listAllEmployees(page);
        return ResponseEntity.ok(pageResult);
    }

}
