package com.samhcoco.managementsystem.employee.repository;

import com.samhcoco.managementsystem.core.model.EmployeeDepartment;
import com.samhcoco.managementsystem.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDepartmentRepository extends BaseRepository<EmployeeDepartment, Long> {
    List<EmployeeDepartment> findAllByDeletedFalse();
}
