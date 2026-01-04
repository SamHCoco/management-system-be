package com.samhcoco.managementsystem.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samhcoco.managementsystem.core.model.Employee;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductInventory;
import com.samhcoco.managementsystem.core.repository.EmployeeRepository;
import com.samhcoco.managementsystem.core.repository.OrderRepository;
import com.samhcoco.managementsystem.core.repository.ProductInventoryRepository;
import com.samhcoco.managementsystem.core.repository.ProductRepository;
import org.hibernate.query.Order;
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
            ProductRepository productRepository,
            ProductInventoryRepository productInventoryRepository,
            OrderRepository orderRepository,
            EmployeeRepository employeeRepository
    ){
        final Map<Class<?>, JpaRepository<?, ?>> jpaRepositories = new HashMap<>();

        jpaRepositories.put(Product.class, productRepository);
        jpaRepositories.put(ProductInventory.class, productInventoryRepository);
        jpaRepositories.put(Order.class, orderRepository);
        jpaRepositories.put(Employee.class, employeeRepository);
        return jpaRepositories;
    }

}
