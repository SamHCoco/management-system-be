package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import com.samhcoco.managementsystem.employee.service.EmployeeService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee findById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Page<Employee> listAllEmployees(@NonNull AppPage appPage) {
        return employeeRepository.findAll(appPage.toPageRequest());
    }

    @Override
    public Employee create(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(@NonNull Employee employee) {
        return employeeRepository.save(employee);
    }
}
