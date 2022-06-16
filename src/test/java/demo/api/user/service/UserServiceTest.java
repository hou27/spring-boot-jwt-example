package demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.completableFuture;
import static org.junit.jupiter.api.Assertions.*;

import demo.api.auth.AuthService;
import demo.api.auth.dtos.SignUpRes;
import demo.api.user.UserService;
import demo.api.user.domain.User;
import demo.api.auth.dtos.SignUpReq;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("User Service Test")
class UserServiceTest {
  private static final String EMAIL = "test@email.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "김정호";

  @Autowired
  private PasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;

  @Test
  @DisplayName("모든 유저 리스트를 반환")
  void findAll() {
    // given
    List<User> prevUserList = userService.findAll();
    int prevLen = prevUserList.size();
    SignUpReq user1 = createSignUpRequest();
    authService.signUp(user1);

    // when
    List<User> userList = userService.findAll();

    // then
    assertEquals(prevLen + 1, userList.size());
  }

  @Test
  @DisplayName("이메일로 유저 찾기")
  void findByEmail() {
    // given
    SignUpReq user1 = createSignUpRequest();
    authService.signUp(user1);

    // when
    Optional<User> byEmail = userService.findByEmail(EMAIL);

    // then
    assertThat(byEmail.get().getEmail()).isEqualTo(user1.getEmail());
  }

  @Test
  void updateUser() {
  }

  private SignUpReq createSignUpRequest() {
    return SignUpReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .build();
  }
}