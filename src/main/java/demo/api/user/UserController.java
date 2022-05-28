package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignInRequest;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.exception.UserNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User 관련 HTTP 요청 처리
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/signUp")
  public String signUp() {
    return "user/signUp";
  }

  @PostMapping("/signUp")
  public String signUp(@Validated UserSignUpRequest signUpReq) throws Exception {
    User user = userService.signUp(signUpReq);

    return "redirect:/user/signIn";
  }

  @GetMapping("/signIn")
  public String signIn(@RequestParam(value = "fail", required = false) String flag, Model model) {
    if(flag == null || !flag.equals("true")) {
      model.addAttribute("failed", false);
      return "user/signIn";
    }
    else {
      model.addAttribute("failed", true);
      return "user/signIn";
    }
  }

  @GetMapping("/profile")
  public String profile(Model model, @AuthenticationPrincipal User user) {
    User userDetail = userService.findByEmail(user.getEmail())
        .orElseThrow(() -> new UserNotFoundException());

    model.addAttribute("userDetail", userDetail);

    return "user/profile";
  }

  @GetMapping("/user/userList")
  public String showUserList(Model model) {
    List<User> userList = userService.findAll();

    model.addAttribute("userList", userList);

    return "user/userList";
  }
}
