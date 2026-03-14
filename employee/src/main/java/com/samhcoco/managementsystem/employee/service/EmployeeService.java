package com.samhcoco.managementsystem.employee.service;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.dto.EmployeeDto;
import com.samhcoco.managementsystem.employee.model.dto.EmployeeRegistrationDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    /**
     * Returns the {@link Employee} specified by the given ID.
     * @param id {@link Employee} ID.
     * @return {@link Employee}.
     */
    Employee findById(long id);

    /**
     * Lists all {@link Employee}s.
     * @param appPage {@link AppPage}
     * @return {@link Page} of {@link EmployeeDto}.
     */
    Page<EmployeeDto> listAllEmployees(AppPage appPage);

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
