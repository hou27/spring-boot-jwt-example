package demo.api.config;

import demo.api.jwt.JwtAccessDeniedHandler;
import demo.api.jwt.JwtAuthenticationEntryPoint;
import demo.api.jwt.JwtTokenFilter;
import demo.api.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
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
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // 
    http
        .csrf().disable()
        .formLogin()
        .loginPage("/user/signIn")
        .loginProcessingUrl("/user/signInProc")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/")
        .failureUrl("/user/signIn?fail=true");

    //
    http
        .authorizeRequests()
        .antMatchers(
            "/",
            "/auth/signUp",
            "/user/userList",
            "/auth/signIn*",
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
