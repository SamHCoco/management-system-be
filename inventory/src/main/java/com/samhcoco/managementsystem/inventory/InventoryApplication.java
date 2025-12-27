package com.samhcoco.managementsystem.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackages = {
        "com.samhcoco.managementsystem.inventory",
        "com.samhcoco.managementsystem.core"
})
@ComponentScan(basePackages = {
        "com.samhcoco.managementsystem.inventory",
        "com.samhcoco.managementsystem.core"
})
@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

}
