package com.smarthr.smarthrspringboot.config;

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
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Permettre toutes les requÃƒÂªtes sans authentification
            )
            .csrf(csrf -> csrf.disable());  // DÃƒÂ©sactiver CSRF pour le dÃƒÂ©veloppement
        
        return http.build();
    }
}