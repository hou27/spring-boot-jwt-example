package com.api.auth;

import com.api.auth.dtos.SignInReq;
import com.api.auth.dtos.SignUpReq;
import com.api.auth.dtos.SignUpRes;
import com.api.jwt.dtos.RegenerateTokenDto;
import com.api.jwt.dtos.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @PostMapping("/signUp")
  public SignUpRes signUp(@Validated SignUpReq signUpReq) {
    return authService.signUp(signUpReq);
  }

  @PostMapping("/signIn")
  public ResponseEntity<TokenDto> signIn(@Validated SignInReq signInReq) {
    return authService.signIn(signInReq);
  }

  @PostMapping("/regenerateToken")
  public ResponseEntity<TokenDto> regenerateToken(@Validated RegenerateTokenDto refreshTokenDto) {
    return authService.regenerateToken(refreshTokenDto);
  }
}
