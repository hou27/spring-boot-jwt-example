package demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import demo.api.AppConfig;
import demo.api.user.UserService;
import demo.api.user.UserServiceImpl;
import demo.api.user.domain.User;
import demo.api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@DisplayName("User Service Test")
class UserServiceTest {
  private static final String EMAIL = "test@email.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "김정호";
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig(userRepository);
    userService = appConfig.userService();
  }

  @Test
  @DisplayName("User Sign Up")
  void signUp() {
    User user = createUser();
    System.out.println("user = " + user.toString());
    User newUser = userService.signUp(user);

    System.out.println("newUser = " + newUser.toString());
    assertThat(newUser.getEmail()).isEqualTo(EMAIL);
  }

  @Test
  void findUserByEmail() {
  }

  @Test
  void updateUserName() {
  }

  @Test
  void isEmailExist() {
  }

  private User createUser() {
    return User.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .build();
  }
}