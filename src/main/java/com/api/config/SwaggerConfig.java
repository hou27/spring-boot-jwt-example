package com.api.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.GroupedOpenApi;
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

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Spring Boot API Example")
            .description("Spring Boot API 예시 프로젝트입니다.")
            .version("v0.0.1"));
  }
}
