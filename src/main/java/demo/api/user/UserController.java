package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User 관련 HTTP 요청 처리
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/signUp")
  public User signUp(@ModelAttribute @Validated UserSignUpRequest signUpReq) throws Exception {
    return userService.signUp(signUpReq);
  }
}
