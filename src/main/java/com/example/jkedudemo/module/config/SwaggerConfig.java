package com.example.jkedudemo.module.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi jwtApi() {
        return GroupedOpenApi.builder()
                .group("Jkedu-demo-api")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Jkedu-demo Spring boot API Example")
                        .description("Jkedu-demo.")
                        .version("v0.0.1"));
    }
}
