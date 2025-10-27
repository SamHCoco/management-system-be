package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.ErrorMessageConfig;
import com.samhcoco.managementsystem.core.service.AbstractEntityValidator;
import com.samhcoco.managementsystem.employee.repository.EmployeeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class EmployeeEntityValidator extends AbstractEntityValidator<Employee, Long, EmployeeRepository> {

    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAMES = "middleNames";
    private static final String LAST_NAME = "lastName";
    private static final String EMPLOYEE_DEPARTMENT_ID = "employeeDepartmentId";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String ADDRESS_FIRST_LINE = "addressFirstLine";
    private static final String ADDRESS_SECOND_LINE = "addressSecondLine";
    private static final String ADDRESS_CITY = "addressCity";
    private static final String ADDRESS_POST_CODE = "addressPostCode";
    private static final String CREATED_AT = "createdAt";
    private static final String LAST_MODIFIED_AT = "lastModifiedAt";
    private static final String ENTITY = "entity";

    @Autowired
    public EmployeeEntityValidator(EmployeeRepository employeeRepository,
                                   ErrorMessageConfig errorMessageConfig) {
        super(employeeRepository, errorMessageConfig);
    }

    @Override
    public Map<String, String> validateCreate(Employee entity) {
        HashMap<String, String> errors = new HashMap<>();

        if (isNull(entity)) {
            errors.put(ENTITY, getErrorMessageConfig().getEntityNull());
            return errors;
        }

        validateCommonFields(entity, errors);

        if (entity.getId() != 0) {
            errors.put(ID, getErrorMessageConfig().getFieldValueInvalid());
        }

        if (!isBlank(entity.getEmail())) {
            if (getRepository().existsByEmail(entity.getEmail())) {
                errors.put(EMAIL, getErrorMessageConfig().getValueAlreadyExists());
            }
        }

        if (!isBlank(entity.getPhone())) {
            if (getRepository().existsByPhone(entity.getPhone())) {
                errors.put(PHONE, getErrorMessageConfig().getValueAlreadyExists());
            }
        }

        return errors;
    }

    @Override
    public Map<String, String> validateUpdate(Employee entity) {
        HashMap<String, String> errors = new HashMap<>();

        if (isNull(entity)) {
            errors.put(ENTITY, getErrorMessageConfig().getEntityNull());
            return errors;
        }

        if (entity.getId() == 0) {
            errors.put(ID, getErrorMessageConfig().getFieldValueInvalid());
        }

        validateCommonFields(entity, errors);

        if (!isBlank(entity.getEmail())) {
            Employee employee = getRepository().findByEmail(entity.getEmail());
            if (nonNull(employee)) {
                if (employee.getId() != entity.getId()) {
                    errors.put(EMAIL, getErrorMessageConfig().getValueAlreadyExists());
                }
            }
        }

        if (!isBlank(entity.getPhone())) {
            Employee employee = getRepository().findByPhone(entity.getPhone());
            if (nonNull(employee)) {
                if (employee.getId() != entity.getId()) {
                    errors.put(PHONE, getErrorMessageConfig().getValueAlreadyExists());
                }
            }
        }
        return errors;
    }

    private void validateCommonFields(Employee entity,
                                                     @NonNull Map<String, String> errors) {

        if (isBlank(entity.getFirstName())) {
            errors.put(FIRST_NAME, getErrorMessageConfig().getNullOrEmpty());
        }

        if (isBlank(entity.getLastName())) {
            errors.put(LAST_NAME, getErrorMessageConfig().getNullOrEmpty());
        }

        if (isBlank(entity.getEmail())) {
            errors.put(EMAIL, getErrorMessageConfig().getNullOrEmpty());
        }

        if (isBlank(entity.getPhone())) {
            errors.put(PHONE, getErrorMessageConfig().getNullOrEmpty());
        }

        if (entity.getEmployeeDepartmentId() == 0) {
            errors.put(EMPLOYEE_DEPARTMENT_ID, getErrorMessageConfig().getFieldValueInvalid());
        }

        if (isBlank(entity.getAddressFirstLine())) {
            errors.put(ADDRESS_FIRST_LINE, getErrorMessageConfig().getNullOrEmpty());
        }

        if (isBlank(entity.getAddressCity())) {
            errors.put(ADDRESS_CITY, getErrorMessageConfig().getNullOrEmpty());
        }

        if (isBlank(entity.getAddressPostCode())) {
            errors.put(ADDRESS_POST_CODE, getErrorMessageConfig().getNullOrEmpty());
        }
    }
}



