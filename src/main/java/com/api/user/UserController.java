package com.api.user;

import com.api.exception.UserNotFoundException;
import com.api.user.domain.Users;
import com.api.user.dtos.ProfileDto.ProfileRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    Users userDetail = userService.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFoundException());

    return ProfileRes.builder()
        .email(userDetail.getEmail())
        .name(userDetail.getName())
        .build();
  }

  @GetMapping("/profile/view/{username}")
  public ProfileRes userProfile(@PathVariable String username) throws UserNotFoundException {
    Users user = userService.findByName(username)
        .orElseThrow(UserNotFoundException::new);

    return ProfileRes.builder()
        .email(user.getEmail())
        .name(user.getName())
        .build();
  }

  @GetMapping("/userList")
  public List<Users> showUserList() {
    return userService.findAll();
  }
}
