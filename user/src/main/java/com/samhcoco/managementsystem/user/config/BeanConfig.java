package com.samhcoco.managementsystem.user.config;

import com.samhcoco.managementsystem.core.model.User;
import com.samhcoco.managementsystem.user.repository.UserRepository;
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
            UserRepository userRepository
    ){
        final Map<Class<?>, JpaRepository<?, ?>> repositories = new HashMap<>();
        repositories.put(User.class, userRepository);
        return repositories;
    }
}
