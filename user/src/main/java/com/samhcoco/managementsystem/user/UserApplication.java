package com.samhcoco.managementsystem.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@EntityScan(basePackages = {
        "com.samhcoco.managementsystem.user",
        "com.samhcoco.managementsystem.core"
})
@ComponentScan(basePackages = {
        "com.samhcoco.managementsystem.user",
        "com.samhcoco.managementsystem.core"
})
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
