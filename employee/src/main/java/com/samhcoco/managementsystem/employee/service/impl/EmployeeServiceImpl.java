package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.keycloak.Credential;
import com.samhcoco.managementsystem.core.model.keycloak.KeycloakUser;
import com.samhcoco.managementsystem.core.service.KeycloakService;
import com.samhcoco.managementsystem.employee.repository.EmployeeRepository;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final KeycloakService keycloakService;

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Page<Employee> listAllEmployees(@NotNull com.samhcoco.managementsystem.core.model.Page page) {
        return employeeRepository.findAll(page.toPageRequest());
    }


    @Override
    @Transactional
    public Employee create(@NotNull Employee employee) {
        var created = employeeRepository.save(employee);

        val credentials = Credential.builder()
                                    .temporary(false)
                                    .value(employee.getPassword())
                                    .build();

        var keycloakUser = KeycloakUser.builder()
                                    .email(employee.getEmail())
                                    .username(employee.getEmail())
                                    .firstName(employee.getFirstName())
                                    .lastName(employee.getLastName())
                                    .emailVerified(true)
                                    .enabled(true)
                                    .attributes(Map.of("userId", created.getId()))
                                    .credentials(List.of(credentials))
                                    .build();

        keycloakUser = keycloakService.create(keycloakUser);

        if (isNull(keycloakUser)) {
            val error = format("Failed to create employee - could not register employee '%s' with Keycloak", employee.getEmail());
            log.error(error);
           throw new RuntimeException(error);
        }

        created.setAuthID(keycloakUser.getId());
        created = employeeRepository.save(employee);
        return created;
    }

    @Override
    public Employee update(@NotNull Employee employee) {
        return employeeRepository.save(employee);
    }
}
