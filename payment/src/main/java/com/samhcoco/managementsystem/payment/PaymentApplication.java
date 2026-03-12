package com.samhcoco.managementsystem.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {
        "com.samhcoco.managementsystem.payment",
        "com.samhcoco.managementsystem.core"
})
@ComponentScan(basePackages = {
        "com.samhcoco.managementsystem.payment",
        "com.samhcoco.managementsystem.core"
})
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}
