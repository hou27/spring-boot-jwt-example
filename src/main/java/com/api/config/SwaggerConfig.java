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
        .addOpenApiCustomiser(buildSecurityOpenApi())
        .build();
  }

  @Bean
  public GroupedOpenApi nonSecurityApi() {
    return GroupedOpenApi.builder()
        .group("authentication isn't required")
        .pathsToExclude("/user/profile")
        .build();
  }

  // https://velog.io/@soyeon207/%EC%9A%B0%EB%8B%B9%ED%83%95%ED%83%95-Swagger-%EC%A0%81%EC%9A%A9%EA%B8%B0#4-%EF%B8%8F-jwt-token-%EC%84%A4%EC%A0%95
  public OpenApiCustomiser buildSecurityOpenApi() {
    // jwt token 설정 시 header에 값을 넣어줌
    return OpenApi -> OpenApi.addSecurityItem(new SecurityRequirement().addList("jwt access token"))
        .getComponents().addSecuritySchemes("jwt access token", new SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .bearerFormat("JWT")
            .scheme("bearer"));
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
