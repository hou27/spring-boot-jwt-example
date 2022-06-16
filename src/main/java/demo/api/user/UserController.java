package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.ProfileDto.ProfileReq;
import demo.api.user.dtos.ProfileDto.ProfileRes;
import demo.api.user.exception.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/profile")
  public ProfileRes profile(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
    System.out.println("userDetails = " + userDetails);
    User userDetail = userService.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFoundException());

    return ProfileRes.builder()
        .email(userDetail.getEmail())
        .name(userDetail.getName())
        .build();
  }

  @GetMapping("/profile/user/{username}")
  public ProfileRes userProfile(@PathVariable String username) throws UserNotFoundException {
    User user = userService.findByName(username)
        .orElseThrow(UserNotFoundException::new);

    return ProfileRes.builder()
        .email(user.getEmail())
        .name(user.getName())
        .build();
  }

  @GetMapping("/userList")
  public List<User> showUserList(Model model) {
    return userService.findAll();
  }
}
