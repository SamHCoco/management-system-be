package com.samhcoco.managementsystem.employee.controller;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeEntityValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api")
@Tag(name = "Employee API", description = "Employee management APIs")
@RequiredArgsConstructor
public class EmployeeController {

    private static final String EMPLOYEE = "employee";

    private final EmployeeService employeeService;
    private final EmployeeEntityValidator employeeEntityValidator;

    @PreAuthorize("hasRole('admin')")
    @PostMapping(ApiVersion.V1 + "/" + EMPLOYEE)
    public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
        Map<String, String> errors = employeeEntityValidator.validateCreate(employee);
        if (errors.isEmpty()) {
            return ResponseEntity.status(CREATED)
                                 .body(employeeService.create(employee));
        }
        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping(ApiVersion.V1 + "/" + EMPLOYEE)
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
        Map<String, String> errors = employeeEntityValidator.validateUpdate(employee);
        if (errors.isEmpty()) {
            return ResponseEntity.status(OK)
                                 .body(employeeService.create(employee));
        }
        return ResponseEntity.status(BAD_REQUEST)
                             .body(errors);
    }

    @PreAuthorize("hasAnyRole('admin','user')")
    @GetMapping(ApiVersion.V1 + "/" + EMPLOYEE)
    public ResponseEntity<Object> listAllEmployees(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) String sort,
                                                   @RequestParam(required = false) String sortDirection) {
        com.samhcoco.managementsystem.core.model.Page pageClass = new com.samhcoco.managementsystem.core.model.Page();
        if (nonNull(page)) {
            pageClass.setPage(page);
        }
        if (nonNull(size)) {
            pageClass.setSize(size);
        }
        if (!isBlank(sort)) {
            pageClass.setSort(sort);
        }
        if (!isBlank(sortDirection)) {
            pageClass.setSortDirection(sortDirection);
        }

        Page<Employee> pageResult = employeeService.listAllEmployees(pageClass);
        return ResponseEntity.ok(pageResult);
    }

}
