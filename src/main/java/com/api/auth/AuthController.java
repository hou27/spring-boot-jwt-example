package com.api.auth;

import com.api.auth.dtos.SignInReq;
import com.api.auth.dtos.SignUpReq;
import com.api.auth.dtos.SignUpRes;
import com.api.jwt.dtos.RegenerateTokenDto;
import com.api.jwt.dtos.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 api")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @Operation(summary = "회원가입", description = "회원가입 메서드입니다.")
  @PostMapping("/signUp")
  public SignUpRes signUp(@Validated SignUpReq signUpReq) {
    return authService.signUp(signUpReq);
  }

  @Operation(summary = "로그인", description = "로그인 메서드입니다.")
  @PostMapping("/signIn")
  public ResponseEntity<TokenDto> signIn(@Validated SignInReq signInReq) {
    return authService.signIn(signInReq);
  }

  @Operation(summary = "토큰 재발행", description = "토큰을 재발행하는 메서드입니다.")
  @PostMapping("/regenerateToken")
  public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
    return authService.regenerateToken(refreshTokenDto);
  }
}
