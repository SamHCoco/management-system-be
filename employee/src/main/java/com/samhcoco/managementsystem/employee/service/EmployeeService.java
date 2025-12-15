package com.samhcoco.managementsystem.employee.service;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.Page;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;

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
    org.springframework.data.domain.Page<Employee> listAllEmployees(@NotNull Page page);

    /**
     * Persists a single new {@link Employee}.
     * @param employee {@link Employee}.
     * @return Persisted {@link Employee}.
     */
    Employee create(@NotNull Employee employee);

    /**
     * Updates the supplied {@link Employee}.
     * @param employee {@link Employee}.
     * @return Updated {@link Employee}.
     */
    Employee update(@NotNull Employee employee);


    // todo - move this method to an AuthorizationService
    boolean isAuthorized(Authentication authentication);

}
