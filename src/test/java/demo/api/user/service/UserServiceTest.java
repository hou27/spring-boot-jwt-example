package demo.api.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import demo.api.auth.AuthService;
import demo.api.user.UserService;
import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.repository.UserRepository;
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
  @DisplayName("유저 회원가입")
  void signUp() throws Exception {
    // given
    UserSignUpRequest user = createSignUpRequest();
    System.out.println("user = " + user.toString());

    // when
    User newUser = authService.signUp(user);

    // then
    System.out.println("newUser = " + newUser.toString());
    assertThat(newUser.getEmail()).isEqualTo(EMAIL);
  }

  @Test
  @DisplayName("비밀번호는 암호화되어야 한다.")
  void hashPassword() throws Exception {
    // given
    UserSignUpRequest user = createSignUpRequest();

    // when
    User newUser = authService.signUp(user);

    // then
    System.out.println("newUser pw = " + newUser.getPassword());
    assertThat(newUser.getPassword()).isNotEqualTo(PASSWORD);
  }

  @Test
  @DisplayName("유저 로그인")
  void signIn() throws Exception {
    // given
    UserSignUpRequest user = createSignUpRequest();
    System.out.println("user = " + user.toString());
    User newUser = authService.signUp(user);

    // when
    boolean flag = newUser.checkPassword(PASSWORD, bCryptPasswordEncoder);
    System.out.println("flag = " + flag);

    // then
  }

  @Test
  @DisplayName("모든 유저 리스트를 반환")
  void findAll() throws Exception {
    // given
    List<User> prevUserList = userService.findAll();
    int prevLen = prevUserList.size();
    UserSignUpRequest user1 = createSignUpRequest();
    authService.signUp(user1);

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
    authService.signUp(user1);

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