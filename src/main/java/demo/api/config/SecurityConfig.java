package demo.api.config;

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

/**
 * Spring Security 사용을 위한 Configuration Class를 작성하기 위해서
 * WebSecurityConfigurerAdapter를 상속하여 클래스를 생성하고
 * @Configuration 애노테이션 대신 @EnableWebSecurity 애노테이션을 추가한다.
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;

  /**
   * PasswordEncoder를 Bean으로 등록
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  /**
//   * 인증 or 인가가 필요 없는 경로를 설정
//   */
//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers("/?/**");
//  }

  /**
   * 인증에 대한 지원
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
  }

  /**
   * 인증 or 인가에 대한 설정
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
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
  }
}
