package com.samhcoco.managementsystem.employee.config;

import com.samhcoco.managementsystem.employee.model.Employee;
import com.samhcoco.managementsystem.employee.repository.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {

    @Bean
    @Primary
    public Map<Class<?>, JpaRepository<?, ?>> jpaRepositories(
            EmployeeRepository employeeRepository
    ){
        final Map<Class<?>, JpaRepository<?, ?>> repositories = new HashMap<>();
        repositories.put(Employee.class, employeeRepository);
        return repositories;
    }

}
