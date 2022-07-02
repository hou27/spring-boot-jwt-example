package com.api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public GroupedOpenApi securityApi() {
    return GroupedOpenApi.builder()
        .group("authentication is required")
        .pathsToMatch("/user/profile")
        .build();
  }

  @Bean
  public GroupedOpenApi nonSecurityApi() {
    return GroupedOpenApi.builder()
        .group("authentication isn't required")
        .pathsToExclude("/user/profile")
        .build();
  }

  // https://swagger.io/docs/specification/authentication/
  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme bearerAuth = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("Bearer");
    SecurityRequirement securityItem = new SecurityRequirement().addList("bearerAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", bearerAuth))
        .addSecurityItem(securityItem)
        .info(new Info().title("Spring Boot API Example")
            .description("Spring Boot API 예시 프로젝트입니다.")
            .version("v0.0.1"));
  }
}
