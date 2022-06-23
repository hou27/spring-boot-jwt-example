package com.api;

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
