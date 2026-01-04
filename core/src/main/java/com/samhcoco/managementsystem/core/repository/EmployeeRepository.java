package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.Employee;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long id);
    Page<Employee> findAll(@NonNull Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);
}
