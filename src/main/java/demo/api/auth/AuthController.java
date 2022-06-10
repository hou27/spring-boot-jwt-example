package demo.api.auth;

import demo.api.jwt.dtos.TokenDto;
import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignInRequest;
import demo.api.user.dtos.UserSignUpRequest;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;

  @GetMapping("/signUp")
  public String signUp() {
    return "user/signUp";
  }

  @PostMapping("/signUp")
  public String signUp(@Validated UserSignUpRequest signUpReq) throws Exception {
    User user = authService.signUp(signUpReq);

    if(!Objects.isNull(user)) {
      return "redirect:/user/signIn";
    }

    return "redirect:/user/signUp";
  }

  @GetMapping("/signIn")
  public String signIn(@RequestParam(value = "fail", required = false) String flag, Model model) {
    model.addAttribute("failed", flag != null);

    return "user/signIn";
  }

  @PostMapping("/signIn")
  public String signIn(@Validated UserSignInRequest signInReq, HttpServletResponse res) {
    ResponseEntity<TokenDto> tokenDtoResponseEntity = authService.signIn(signInReq);
    Cookie cookie = new Cookie(
        "access_token",
        tokenDtoResponseEntity.getBody().getAccess_token()
    );

    cookie.setPath("/");
    cookie.setMaxAge(Integer.MAX_VALUE);

    res.addCookie(cookie);
    return "redirect:/user/profile";
  }
}
