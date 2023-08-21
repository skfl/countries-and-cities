package com.skfl.city.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
        http
                .authorizeHttpRequests
                        (auth -> auth.requestMatchers(HttpMethod.GET,
                                        "/api/v1/city",
                                        "/api/v1/city/names/unique",
                                        "/actuator/**",
                                        "/api/v1/city/logo/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/v1/city/**")
                                .hasRole("EDITOR")
                                .requestMatchers(HttpMethod.POST,"/api/v1/city/**")
                                .hasRole("EDITOR")
                                .requestMatchers("/swagger-ui/**")
                                .permitAll()
                                .anyRequest().permitAll())
                .oauth2ResourceServer(oauth -> oauth.jwt(customizer -> customizer.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
