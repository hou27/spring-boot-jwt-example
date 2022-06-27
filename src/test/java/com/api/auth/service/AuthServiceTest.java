package com.api.auth.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.api.auth.AuthService;
import com.api.auth.dtos.SignInReq;
import com.api.auth.dtos.SignUpReq;
import com.api.auth.dtos.SignUpRes;
import com.api.exception.UserNotFoundException;
import com.api.jwt.dtos.RegenerateTokenDto;
import com.api.jwt.dtos.TokenDto;
import com.api.user.UserService;
import com.api.user.domain.UserRole;
import com.api.user.domain.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DisplayName("Auth Service Test")
class AuthServiceTest {
  private static final String EMAIL = "test@email.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "김정호";
  private static final UserRole role = UserRole.ROLE_ADMIN;

  @Autowired
  private UserService userService;
  @Autowired
  private AuthService authService;

  @Nested
  @DisplayName("유저 회원가입")
  class signUp {
    @Test
    @DisplayName("유저 Role 기본값은 Client이다.")
    void clientSignUp() {
      // given
      SignUpReq user = createSignUpRequestWithoutRole();
      System.out.println("user = " + user.toString());

      // when
      SignUpRes signUpRes = authService.signUp(user);

      // then
      Assertions.assertThat(signUpRes.isOk()).isEqualTo(true);
      Assertions.assertThat(
          userService.findByEmail(EMAIL).get().getRole()
      ).isEqualTo(UserRole.ROLE_CLIENT);
    }
    @Test
    @DisplayName("유저 Role을 지정해주면 그 값이 적용된다.")
    void adminSignUp() {
      // given
      SignUpReq user = createSignUpRequestWithRole();
      System.out.println("user = " + user.toString());

      // when
      SignUpRes signUpRes = authService.signUp(user);

      // then
      Assertions.assertThat(signUpRes.isOk()).isEqualTo(true);
      Assertions.assertThat(
          userService.findByEmail(EMAIL).get().getRole()
      ).isEqualTo(UserRole.ROLE_ADMIN);
    }
  }

  @Test
  @DisplayName("유저 로그인")
  void signIn() {
    // given
    SignUpReq user = createSignUpRequestWithoutRole();
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
    SignUpReq user = createSignUpRequestWithoutRole();

    // when
    authService.signUp(user);

    // then
    Users createdUser = userService.findByEmail(EMAIL)
        .orElseThrow(UserNotFoundException::new);
    System.out.println("newUser pw = " + createdUser.getPassword());

    assertThat(createdUser.getPassword()).isNotEqualTo(PASSWORD);
  }

  @Test
  @DisplayName("토큰 재발행")
  void regenerateToken() {
    // given
    SignUpReq user = createSignUpRequestWithoutRole();
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

  private SignUpReq createSignUpRequestWithoutRole() {
    return SignUpReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .build();
  }

  private SignUpReq createSignUpRequestWithRole() {
    return SignUpReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .name(NAME)
        .role(role)
        .build();
  }

  private SignInReq createSignInRequest() {
    return SignInReq.builder()
        .email(EMAIL)
        .password(PASSWORD)
        .build();
  }
}