package com.samhcoco.managementsystem.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import com.samhcoco.managementsystem.core.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class BeanConfiguration {

    // todo - configure timeout
    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public Map<Class<?>, JpaRepository<?, ?>> jpaRepositories(
            EmployeeRepository employeeRepository,
            UserRepository userRepository
    ){
        final Map<Class<?>, JpaRepository<?, ?>> repositories = new HashMap<>();
        repositories.put(Employee.class, employeeRepository);
        repositories.put(User.class, userRepository);

        return repositories;
    }

}
