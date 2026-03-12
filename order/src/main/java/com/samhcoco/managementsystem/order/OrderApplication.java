package com.samhcoco.managementsystem.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.samhcoco.managementsystem.order",
        "com.samhcoco.managementsystem.core"
})
@ComponentScan(basePackages = {
        "com.samhcoco.managementsystem.order",
        "com.samhcoco.managementsystem.core"
})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
