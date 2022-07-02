package com.api.user;

import com.api.exception.UserNotFoundException;
import com.api.user.domain.Users;
import com.api.user.dtos.ProfileDto.ProfileRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "유저 관련 api")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @Operation(summary = "유저 개인 프로필", description = "유저 프로필 조회 메서드입니다.")
  @GetMapping("/profile")
  public ProfileRes profile(@AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
    System.out.println("userDetails = " + userDetails);
    Users userDetail = userService.findByEmail(userDetails.getUsername())
        .orElseThrow(() -> new UserNotFoundException());

    System.out.println("userDetails.getAuthorities " + userDetails.getAuthorities());
    return ProfileRes.builder()
        .email(userDetail.getEmail())
        .name(userDetail.getName())
        .build();
  }

  @Operation(summary = "지정된 유저의 프로필", description = "특정 유저 프로필 조회 메서드입니다.")
  @GetMapping("/profile/view/{username}")
  public ProfileRes userProfile(@PathVariable String username) throws UserNotFoundException {
    Users user = userService.findByName(username)
        .orElseThrow(UserNotFoundException::new);

    return ProfileRes.builder()
        .email(user.getEmail())
        .name(user.getName())
        .build();
  }

  @Operation(summary = "전체 유저 정보", description = "전체 유저 정보 조회 메서드입니다.")
  @GetMapping("/userList")
  public List<Users> showUserList() {
    return userService.findAll();
  }
}
