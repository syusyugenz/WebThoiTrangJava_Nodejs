package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.cors().and().csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests -> requests
//			.requestMatchers("/login", "/register", "/reset-password", "/", "/product-detail").permitAll()
		.anyRequest().permitAll());
	return http.build();
    }


}