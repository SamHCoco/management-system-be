package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Employee findByEmail(String email);
    Employee findByPhone(String phone);

    @Query(value = """
                  SELECT 
                      e.id as id,
                      e.auth_id as authId,
                      e.first_name as firstName,
                      e.middle_names as middleNames,
                      e.last_name as lastName,
                      e.department_id as departmentId,
                      e.email as email,
                      e.phone as phone,
                      e.address_first_line as addressFirstLine,
                      e.address_second_line as addressSecondLine,
                      e.address_city as addressCity,
                      e.address_post_code as addressPostCode
                  FROM employee e
                  WHERE e.deleted = 0
                  """,
            countQuery = "SELECT COUNT(*) FROM employee e WHERE e.deleted = 0",
            nativeQuery = true)
    Page<EmployeeDto> findAllByDeletedFalse(Pageable pageable);
}
