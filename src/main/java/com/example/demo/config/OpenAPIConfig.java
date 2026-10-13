package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservice de Gestion des Comptes Bancaires")
                        .version("1.0.0")
                        .description("Documentation interactive Swagger/OpenAPI des API RESTful pour la gestion des comptes bancaires.")
                        .contact(new Contact()
                                .name("Support API")
                                .email("contact@bank-microservice.com")));
    }
}
