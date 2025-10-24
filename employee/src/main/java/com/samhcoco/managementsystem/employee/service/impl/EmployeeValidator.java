package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.ErrorMessageConfig;
import com.samhcoco.managementsystem.core.service.AbstractEntityValidator;
import com.samhcoco.managementsystem.core.service.EntityValidator;
import com.samhcoco.managementsystem.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class EmployeeValidator extends AbstractEntityValidator<Employee, Long> {

    @Autowired
    public EmployeeValidator(EmployeeRepository employeeRepository,
                             ErrorMessageConfig errorMessageConfig) {
        super(employeeRepository, errorMessageConfig);
    }

    @Override
    public Map<String, String> validateCreate(Employee entity) {
        HashMap<String, String> errors = new HashMap<>();
        System.out.println(getErrorMessageConfig().getNullOrEmpty());
// todo - complete
//
//        if (isNull(entity)) {
//            errors.put("object", "Employee object cannot be null");
//        } else {
//            if (entity.getId() != 0) {
//                errors.put("id", "ID value must not be set for create operation");
//            }
//            if (isBlank(entity.getFirstName())) {
//                errors.put("firstName", "Cannot be null or empty");
//            }
//        }
        return errors;
    }

    @Override
    public Map<String, String> validateUpdate(Employee entity) {
        return Map.of();
    }
}



