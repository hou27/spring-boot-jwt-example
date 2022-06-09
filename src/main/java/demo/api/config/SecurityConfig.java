package demo.api.config;

import demo.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .formLogin()
        .loginPage("/user/signIn")
        .loginProcessingUrl("/user/signInProc")
        .usernameParameter("email")
        .passwordParameter("password")
        .defaultSuccessUrl("/")
        .failureUrl("/user/signIn?fail=true");
    http
        .authorizeRequests()
        .antMatchers("/", "/user/signUp", "/user/userList", "/user/signIn*").permitAll()
        .anyRequest().authenticated();
    
    return http.build();
  }
}
