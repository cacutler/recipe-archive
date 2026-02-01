package com.cacutler.recipearchive.config;

import com.cacutler.recipearchive.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - anyone can access
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll() // User registration
                        .requestMatchers(HttpMethod.GET, "/recipes/**").permitAll() // View recipes
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll() // View users

                        // Protected endpoints - require authentication
                        .requestMatchers(HttpMethod.POST, "/recipes").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/recipes/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/recipes/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()

                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}