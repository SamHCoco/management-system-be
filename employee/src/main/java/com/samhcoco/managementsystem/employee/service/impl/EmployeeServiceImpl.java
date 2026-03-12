package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.enums.KeycloakRoles;
import com.samhcoco.managementsystem.core.exception.UserCreationFailedException;
import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.AuthUser;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.employee.model.EmployeeRegistrationDto;
import com.samhcoco.managementsystem.core.model.keycloak.Credential;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import com.samhcoco.managementsystem.core.service.AuthService;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_ID = "employeeId";

    private final AuthService authService;
    private final EmployeeRepository employeeRepository;

    @Override
    public Employee findById(long id) {
        return employeeRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public Page<Employee> listAllEmployees(@NonNull AppPage appPage) {
        return employeeRepository.findAll(appPage.toPageRequest());
    }

    @Override
    @Transactional
    public Employee create(@NonNull EmployeeRegistrationDto employeeRegistrationDto) {
        employeeRegistrationDto.setAuthId("TEMP-ID");

        val createdEmployee = employeeRepository.save(employeeRegistrationDto.toEmployee());

        final Credential credential = Credential.builder()
                                                .temporary(false)
                                                .value(employeeRegistrationDto.getPassword())
                                                .build();

        final Map<String, List<String>> customJwtClaims = Map.of(EMPLOYEE_ID,
                                                                 singletonList(String.valueOf(createdEmployee.getId())));

        final Set<String> roles = new HashSet<>();
        roles.add(KeycloakRoles.EMPLOYEE.name().toLowerCase());

        if (employeeRegistrationDto.isAdmin()) {
            roles.add(KeycloakRoles.ADMIN.name().toLowerCase());
        }

        AuthUser authUser = authService.createUser(createdEmployee.toAuthUser(
                                                credential,
                                                customJwtClaims,
                                                roles));

        if (authUser == null) {
            employeeRegistrationDto.removePassword();
            throw new UserCreationFailedException("Keycloak", Map.of("Keycloak", format("Failed to register '%s' with Keycloak", employeeRegistrationDto)));
        }

        createdEmployee.setAuthId(authUser.getAuthId());
        return employeeRepository.save(createdEmployee);
    }

    @Override
    public Employee update(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }
}
