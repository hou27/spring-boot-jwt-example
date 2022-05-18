package demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import demo.api.AppConfig;
import demo.api.user.UserService;
import demo.api.user.UserServiceImpl;
import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("User Service Test")
class UserServiceTest {
  private static final String EMAIL = "test@email.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "김정호";
  private UserService userService;
  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig(userRepository);
    userService = appConfig.userService();
  }

  @Test
  @DisplayName("유저 회원가입")
  void signUp() throws Exception {
    UserSignUpRequest user = createSignUpRequest();
    System.out.println("user = " + user.toString());
    User newUser = userService.signUp(user);

    System.out.println("newUser = " + newUser.toString());
    assertThat(newUser.getEmail()).isEqualTo(EMAIL);
  }

  @Test
  @DisplayName("모든 유저 리스트를 반환")
  void findAll() throws Exception {
    // given
    List<User> prevUserList = userService.findAll();
    int prevLen = prevUserList.size();
    UserSignUpRequest user1 = createSignUpRequest();
    userService.signUp(user1);

    // when
    List<User> userList = userService.findAll();

    // then
    assertEquals(prevLen + 1, userList.size());
  }

  @Test
  @DisplayName("이메일로 유저 찾기")
  void findByEmail() throws Exception {
    // given
    UserSignUpRequest user1 = createSignUpRequest();
    userService.signUp(user1);

    // when
    Optional<User> byEmail = userService.findByEmail(EMAIL);

    // then
    assertThat(byEmail.get().getEmail()).isEqualTo(user1.getEmail());
  }

  @Test
  void updateUser() {
  }

  private UserSignUpRequest createSignUpRequest() {
    return UserSignUpRequest.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .build();
  }
}