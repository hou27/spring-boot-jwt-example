package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * User 관련 HTTP 요청 처리
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/profile")
  public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
    System.out.println("userDetails = " + userDetails);
    if (userDetails != null) {
      User userDetail = userService.findByEmail(userDetails.getUsername())
          .orElseThrow(() -> new UserNotFoundException());

      model.addAttribute("userDetail", userDetail);
    }

    return "user/profile";
  }

  @GetMapping("/userList")
  public String showUserList(Model model) {
    List<User> userList = userService.findAll();
    model.addAttribute("userList", userList);

    return "user/userList";
  }
}
