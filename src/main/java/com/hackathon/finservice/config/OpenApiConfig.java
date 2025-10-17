package com.hackathon.finservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${swagger.server.url}")
    private String swaggerServerUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CaixaBank Banking API - Extended")
                        .version("1.0")
                        .description("RESTful Banking API built with Spring Boot as part of the CaixaBank Hackathon. " +
                                     "Provides endpoints for authentication, accounts, transactions, and monitoring.")
                        .contact(new Contact()
                                .name("Kenzo de Albuquerque")
                                .email("kenzoalbuqk@gmail.com")
                                .url("https://github.com/OzneKx")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")
                        )
                ).components(new Components()
                        .addSecuritySchemes("bearerAuth",
                            new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                ).addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .servers(List.of(new Server()
                        .url(swaggerServerUrl)
                        .description("Local or container environment")
                ));
    }
}
