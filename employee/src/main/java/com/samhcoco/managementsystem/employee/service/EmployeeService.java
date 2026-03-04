package com.samhcoco.managementsystem.employee.service;

import com.samhcoco.managementsystem.employee.model.Employee;
import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.employee.model.EmployeeRegistrationDto;

public interface EmployeeService {

    /**
     * Returns the {@link Employee} specified by the given ID.
     * @param id {@link Employee} ID.
     * @return {@link Employee}.
     */
    Employee findById(long id);

    /**
     * Returns all {@link Employee}.
     * @param appPage {@link AppPage}
     * @return {@link org.springframework.data.domain.Page} of {@link Employee}.
     */
    org.springframework.data.domain.Page<Employee> listAllEmployees(AppPage appPage);

    /**
     * Persists a single new {@link Employee} from the given {@link EmployeeRegistrationDto}
     * and register them with the Auth Server.
     * @param employeeRegistrationDto {@link EmployeeRegistrationDto}.
     * @return Persisted {@link Employee}.
     */
    Employee create(EmployeeRegistrationDto employeeRegistrationDto);

    /**
     * Updates the supplied {@link Employee}.
     * @param employee {@link Employee}.
     * @return Updated {@link Employee}.
     */
    Employee update(Employee employee);

}
