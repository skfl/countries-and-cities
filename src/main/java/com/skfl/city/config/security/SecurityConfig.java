package com.skfl.city.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // каждый энпоинт делать рестрикшен + POST GET ...
        http
                .authorizeHttpRequests
                        (auth -> auth.requestMatchers("/api/v1/city", "/api/v1/city/unique","/actuator/**","/api/v1/logo/**")
                                .permitAll()
                                .requestMatchers("/api/v1/city/**")
                                .hasRole("EDITOR")
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(customizer -> customizer.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
