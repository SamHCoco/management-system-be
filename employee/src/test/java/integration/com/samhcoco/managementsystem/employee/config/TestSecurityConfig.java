package integration.com.samhcoco.managementsystem.employee.config;

import com.samhcoco.managementsystem.core.service.KeycloakService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.mockito.Mockito.mock;

@TestConfiguration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Profile("test")
public class TestSecurityConfig {

    @Bean
    @Primary
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz.anyRequest().permitAll());
        return http.build();
    }

    @Bean
    @Primary
    public KeycloakService keycloakService() {
        return mock(KeycloakService.class);
    }
}