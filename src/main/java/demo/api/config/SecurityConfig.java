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
//  private final UserRepository userRepository;
  // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
  // https://github.com/spring-projects/spring-security/issues/10822
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
//    http
//        .authorizeHttpRequests((authz) -> authz
//            .anyRequest().authenticated()
//        )
//        .httpBasic(withDefaults());
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



///**
// * Spring Security 사용을 위한 Configuration Class를 작성하기 위해서
// * WebSecurityConfigurerAdapter를 상속하여 클래스를 생성하고
// * @Configuration 애노테이션 대신 @EnableWebSecurity 애노테이션을 추가한다.
// */
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  private final UserDetailsService userDetailsService;
//
//  /**
//   * PasswordEncoder를 Bean으로 등록
//   */
//  @Bean
//  public BCryptPasswordEncoder bCryptPasswordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
////  /**
////   * 인증 or 인가가 필요 없는 경로를 설정
////   */
////  @Override
////  public void configure(WebSecurity web) throws Exception {
////    web.ignoring().antMatchers("/?/**");
////  }
//
//  /**
//   * 인증에 대한 지원
//   */
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//  }
//
//  /**
//   * 인증 or 인가에 대한 설정
//   */
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//        .csrf().disable()
//        .formLogin()
//        .loginPage("/user/signIn")
//        .loginProcessingUrl("/user/signInProc")
//        .usernameParameter("email")
//        .passwordParameter("password")
//        .defaultSuccessUrl("/")
//        .failureUrl("/user/signIn?fail=true");
//    http
//        .authorizeRequests()
//        .antMatchers("/", "/user/signUp", "/user/userList", "/user/signIn*").permitAll()
//        .anyRequest().authenticated();
//  }
//}
