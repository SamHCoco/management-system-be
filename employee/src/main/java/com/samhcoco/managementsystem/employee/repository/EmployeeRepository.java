package com.samhcoco.managementsystem.employee.repository;

import com.samhcoco.managementsystem.core.model.Employee;

import com.samhcoco.managementsystem.core.model.record.EmployeeDto;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(long id);
    Page<Employee> findAll(@NonNull Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);

    @Query(value = """
        SELECT 
            e.id,
            e.first_name as firstName,
            e.last_name as lastName,
            e.email,
            e.phone,
            ed.name as department
        FROM employee e 
        JOIN employee_department ed ON e.employee_department_id = ed.id
        """,
            countQuery = "SELECT COUNT(*) FROM employee e",
            nativeQuery = true)
    Page<EmployeeDto> findAllWithDepartment(Pageable pageable);
}
