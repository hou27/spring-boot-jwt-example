package demo.api;

import demo.api.user.UserService;
import demo.api.user.UserServiceImpl;
import demo.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
//public class AppConfig {
//  private final UserRepository userRepository;
//  private final PasswordEncoder bCryptPasswordEncoder;
//
//  public AppConfig(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder) {
//    System.out.println("AppConfig");
//    System.out.println("userRepository = " + userRepository);
//    this.userRepository = userRepository;
//    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//  }
//
//  @Bean
//  public UserService userService() {
//    System.out.println("userService");
//    return new UserServiceImpl(userRepository, bCryptPasswordEncoder);
//  }
//
////  @Bean
////  public BCryptPasswordEncoder passwordEncoder() {
////    System.out.println("passwordEncoder");
////    return new BCryptPasswordEncoder();
////  }
//}
