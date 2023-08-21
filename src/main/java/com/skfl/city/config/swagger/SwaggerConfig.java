package com.skfl.city.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {

        return GroupedOpenApi.builder()
                .group("skfl-city")
                .pathsToMatch("/api/v1/**")
                .packagesToScan("com.skfl.city.controller")
                .build();
    }

    @Bean
    public OpenAPI affinityBankOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Countries and cities API")
                        .description("Countries and Cities demo application by skfl")
                        .version("v0.0.1")).externalDocs(new ExternalDocumentation()
                        .description("Authors telegram")
                        .url("https://t.me/ssskyfall"));
    }
}