package demo.api;

import demo.api.user.UserService;
import demo.api.user.UserServiceImpl;
import demo.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  private final UserRepository userRepository;

  public AppConfig(UserRepository userRepository) {
    System.out.println("AppConfig");
    System.out.println("userRepository = " + userRepository);
    this.userRepository = userRepository;
  }

  @Bean
  public UserService userService() {
    System.out.println("userService");
    return new UserServiceImpl(userRepository);
  }

//  @Bean
//  public BCryptPasswordEncoder passwordEncoder() {
//    System.out.println("passwordEncoder");
//    return new BCryptPasswordEncoder();
//  }
}
