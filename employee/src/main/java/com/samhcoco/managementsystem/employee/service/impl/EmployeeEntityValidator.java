package com.samhcoco.managementsystem.employee.service.impl;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.errorMessages;
import com.samhcoco.managementsystem.core.service.EntityValidator;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class EmployeeEntityValidator extends EntityValidator<Employee, Long, EmployeeRepository> {

    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String MIDDLE_NAMES = "middleNames";
    public static final String LAST_NAME = "lastName";
    public static final String EMPLOYEE_DEPARTMENT_ID = "employeeDepartmentId";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ADDRESS_FIRST_LINE = "addressFirstLine";
    public static final String ADDRESS_SECOND_LINE = "addressSecondLine";
    public static final String ADDRESS_CITY = "addressCity";
    public static final String ADDRESS_POST_CODE = "addressPostCode";
    public static final String CREATED_AT = "createdAt";
    public static final String LAST_MODIFIED_AT = "lastModifiedAt";
    public static final String ENTITY = "entity";

    @Autowired
    public EmployeeEntityValidator(EmployeeRepository employeeRepository,
                                   errorMessages errorMessageConfig) {
        super(employeeRepository, errorMessageConfig);
    }

    @Override
    public Map<String, String> validateCreate(Employee entity) {
        HashMap<String, String> errors = new HashMap<>();

        if (isNull(entity)) {
            errors.put(ENTITY, getErrorMessages().getEntityNull());
            return errors;
        }

        validateCommonFields(entity, errors);

        if (entity.getId() != 0) {
            errors.put(ID, getErrorMessages().getFieldValueInvalid());
        }

        if (!isBlank(entity.getEmail())) {
            if (getRepository().existsByEmail(entity.getEmail())) {
                errors.put(EMAIL, getErrorMessages().getValueAlreadyExists());
            }
        }

        if (!isBlank(entity.getPhone())) {
            if (getRepository().existsByPhone(entity.getPhone())) {
                errors.put(PHONE, getErrorMessages().getValueAlreadyExists());
            }
        }

        return errors;
    }

    @Override
    public Map<String, String> validateUpdate(Employee entity) {
        HashMap<String, String> errors = new HashMap<>();

        if (isNull(entity)) {
            errors.put(ENTITY, getErrorMessages().getEntityNull());
            return errors;
        }

        if (entity.getId() == 0) {
            errors.put(ID, getErrorMessages().getFieldValueInvalid());
        }

        validateCommonFields(entity, errors);

        if (!isBlank(entity.getEmail())) {
            Employee employee = getRepository().findByEmail(entity.getEmail());
            if (nonNull(employee)) {
                if (employee.getId() != entity.getId()) {
                    errors.put(EMAIL, getErrorMessages().getValueAlreadyExists());
                }
            }
        }

        if (!isBlank(entity.getPhone())) {
            Employee employee = getRepository().findByPhone(entity.getPhone());
            if (nonNull(employee)) {
                if (employee.getId() != entity.getId()) {
                    errors.put(PHONE, getErrorMessages().getValueAlreadyExists());
                }
            }
        }
        return errors;
    }

    /**
     * Validates common fields in update and create operations.
     * @param entity Errors.
     */
    private void validateCommonFields(Employee entity,
                                      @NonNull Map<String, String> errors) {

        if (isBlank(entity.getFirstName())) {
            errors.put(FIRST_NAME, getErrorMessages().getNullOrEmpty());
        }

        if (isBlank(entity.getLastName())) {
            errors.put(LAST_NAME, getErrorMessages().getNullOrEmpty());
        }

        if (isBlank(entity.getEmail())) {
            errors.put(EMAIL, getErrorMessages().getNullOrEmpty());
        }

        if (isBlank(entity.getPhone())) {
            errors.put(PHONE, getErrorMessages().getNullOrEmpty());
        }

        if (entity.getDepartmentId() == 0) {
            errors.put(EMPLOYEE_DEPARTMENT_ID, getErrorMessages().getFieldValueInvalid());
        }

        if (isBlank(entity.getAddressFirstLine())) {
            errors.put(ADDRESS_FIRST_LINE, getErrorMessages().getNullOrEmpty());
        }

        if (isBlank(entity.getAddressCity())) {
            errors.put(ADDRESS_CITY, getErrorMessages().getNullOrEmpty());
        }

        if (isBlank(entity.getAddressPostCode())) {
            errors.put(ADDRESS_POST_CODE, getErrorMessages().getNullOrEmpty());
        }
    }
}



