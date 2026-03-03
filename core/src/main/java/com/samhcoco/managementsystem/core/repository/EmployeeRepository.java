package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
}
