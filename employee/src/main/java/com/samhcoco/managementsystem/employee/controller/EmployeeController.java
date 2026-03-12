package com.samhcoco.managementsystem.employee.controller;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.employee.model.EmployeeRegistrationDto;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import com.samhcoco.managementsystem.employee.service.impl.EmployeeEntityValidator;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequiredArgsConstructor
@Tag(name = "Employee API", description = "Employee management APIs")
public class EmployeeController {

    private static final String EMPLOYEES = "employees";

    private final EmployeeService employeeService;
    private final EmployeeEntityValidator employeeEntityValidator;

    @PreAuthorize("hasRole('admin')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(ApiVersion.VERSION_1 + "/" + EMPLOYEES)
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeRegistrationDto employeeRegistrationDto) {
        final Map<String, String> errors = employeeEntityValidator.validateCreate(employeeRegistrationDto.toEmployee());
        if (errors.isEmpty()) {
            return ResponseEntity.status(CREATED).body(employeeService.create(employeeRegistrationDto));
        }
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @PreAuthorize("hasRole('admin')")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(ApiVersion.VERSION_1 + "/" + EMPLOYEES)
    public ResponseEntity<Object> updateEmployee(@RequestBody Employee employee) {
        Map<String, String> errors = employeeEntityValidator.validateUpdate(employee);

        if (errors.isEmpty()) {
            return ResponseEntity.status(OK).body(employeeService.update(employee));
        }
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @PreAuthorize("hasAnyRole('admin','employee')")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(ApiVersion.VERSION_1 + "/" + EMPLOYEES)
    public ResponseEntity<Object> listAllEmployees(@RequestParam(required = false) Integer page,
                                                   @RequestParam(required = false) Integer size,
                                                   @RequestParam(required = false) String sort,
                                                   @RequestParam(required = false) String sortDirection) {
        AppPage appPage = new AppPage();
        if (nonNull(page)) {
            appPage.setPage(page);
        }
        if (nonNull(size)) {
            appPage.setSize(size);
        }
        if (!isBlank(sort)) {
            appPage.setSort(sort);
        }
        if (!isBlank(sortDirection)) {
            appPage.setSortDirection(sortDirection);
        }

        Page<Employee> pageResult = employeeService.listAllEmployees(appPage);
        return ResponseEntity.ok(pageResult);
    }

}
