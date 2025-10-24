package com.samhcoco.managementsystem.employee.service;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.Page;

import java.util.Map;

public interface EmployeeService {

    /**
     * Returns the {@link Employee} specified by the given ID.
     * @param id {@link Employee} ID.
     * @return {@link Employee}.
     */
    Employee findById(long id);

    /**
     * Returns all {@link Employee}.
     * @param page {@link Page}
     * @return {@link org.springframework.data.domain.Page} of {@link Employee}.
     */
    org.springframework.data.domain.Page<Employee> listAllEmployees(Page page);

    /**
     * Persists a single new {@link Employee}.
     * @param employee {@link Employee}.
     * @return Persisted {@link Employee}.
     */
    Employee create(Employee employee);

    /**
     * Updates the supplied {@link Employee}.
     * @param employee {@link Employee}.
     * @return Updated {@link Employee}.
     */
    Employee update(Employee employee);

}
