package com.samhcoco.managementsystem.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.samhcoco.managementsystem.employee",
        "com.samhcoco.managementsystem.core"
})
@ComponentScan(basePackages = {
        "com.samhcoco.managementsystem.employee",
        "com.samhcoco.managementsystem.core"
})
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }

}
