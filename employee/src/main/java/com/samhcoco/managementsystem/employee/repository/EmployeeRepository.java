package com.samhcoco.managementsystem.employee.repository;

import com.samhcoco.managementsystem.employee.model.Employee;
import com.samhcoco.managementsystem.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
}
