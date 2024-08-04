package com.speria.vinicius.taskManagementApi.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Atualizado para a versão 6.1
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/**").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
