package demo.api.auth;

import demo.api.auth.dtos.SignUpRes;
import demo.api.jwt.dtos.TokenDto;
import demo.api.auth.dtos.SignInReq;
import demo.api.auth.dtos.SignUpReq;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
  public ResponseEntity<TokenDto> signIn(@Validated SignInReq signInReq, HttpServletResponse res) {
    return authService.signIn(signInReq);
  }
}
