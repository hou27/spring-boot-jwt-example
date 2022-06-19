package demo.api.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import demo.api.auth.AuthService;
import demo.api.auth.dtos.SignInReq;
import demo.api.auth.dtos.SignUpReq;
import demo.api.auth.dtos.SignUpRes;
import demo.api.exception.UserNotFoundException;
import demo.api.jwt.dtos.RegenerateTokenDto;
import demo.api.jwt.dtos.TokenDto;
import demo.api.user.UserService;
import demo.api.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("Auth Service Test")
class AuthServiceTest {
  private static final String EMAIL = "test@email.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "김정호";

  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;

  @Test
  @DisplayName("유저 회원가입")
  void signUp() {
    // given
    SignUpReq user = createSignUpRequest();
    System.out.println("user = " + user.toString());

    // when
    SignUpRes signUpRes = authService.signUp(user);

    // then
    assertThat(signUpRes.isOk()).isEqualTo(true);
  }

  @Test
  @DisplayName("유저 로그인")
  void signIn() {
    // given
    SignUpReq user = createSignUpRequest();
    System.out.println("user = " + user.toString());
    authService.signUp(user);

    // when
    ResponseEntity<TokenDto> response = authService.signIn(createSignInRequest());

    // then
    assertThat(response.getBody().getAccess_token()).isNotEmpty();
    assertThat(response.getBody().getRefresh_token()).isNotEmpty();
  }

  @Test
  @DisplayName("비밀번호는 암호화되어야 한다.")
  void hashPassword() {
    // given
    SignUpReq user = createSignUpRequest();

    // when
    authService.signUp(user);

    // then
    User createdUser = userService.findByEmail(EMAIL)
        .orElseThrow(UserNotFoundException::new);
    System.out.println("newUser pw = " + createdUser.getPassword());

    assertThat(createdUser.getPassword()).isNotEqualTo(PASSWORD);
  }

  @Test
  @DisplayName("토큰 재발행")
  void regenerateToken() {
    // given
    SignUpReq user = createSignUpRequest();
    System.out.println("user = " + user.toString());
    authService.signUp(user);

    // when
    ResponseEntity<TokenDto> response = authService.signIn(createSignInRequest());
    String prevAccessToken = response.getBody().getAccess_token();

    RegenerateTokenDto regenerateTokenDto = new RegenerateTokenDto(
        response.getBody().getRefresh_token()
    );

    ResponseEntity<TokenDto> regeneratedToken = authService.regenerateToken(regenerateTokenDto);

    // then
    assertThat(regeneratedToken.getBody().getAccess_token()).isNotEqualTo(prevAccessToken);
  }

  private SignUpReq createSignUpRequest() {
    return SignUpReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .build();
  }

  private SignInReq createSignInRequest() {
    return SignInReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }
}