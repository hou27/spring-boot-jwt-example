package com.api.config;

import com.api.jwt.JwtAccessDeniedHandler;
import com.api.jwt.JwtAuthenticationEntryPoint;
import com.api.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  // 추가된 jwt 관련 친구들을 security config에 추가
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // Disable csrf to use token
    http
        .csrf().disable();

    //
    http
        .authorizeRequests()
        .antMatchers(
            "/",
            "/auth/signUp",
            "/user/userList",
            "/auth/signIn*",
            "/user/profile/view/**",
            "/auth/regenerateToken",
            "/swagger-ui*/**",
            "/favicon.ico"
        ).permitAll()
        .anyRequest().authenticated();

    // No session will be created or used by spring security
    http
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // exception handling for jwt
    http
        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint);

    // Apply JWT
    http.apply(new JwtSecurityConfig(jwtTokenProvider));

    return http.build();
  }
}
