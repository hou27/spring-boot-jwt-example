package com.api.auth.dtos;

import com.api.user.domain.UserRole;
import com.api.user.domain.Users;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class SignUpReq {
  @NotEmpty(message = "Please enter your Email")
  @Email
  private String email;
  @NotEmpty(message = "Please enter your Password")
  private String password;
  @NotEmpty(message = "Please enter your Name")
  private String name;
  private UserRole role;

  /**
   * Transform to User Entity
   * @return User Entity
   */
  public Users toUserEntity() {
    if(this.getRole() != null) {
      return Users.builder()
          .email(this.getEmail())
          .password(this.getPassword())
          .name(this.getName())
          .role(this.getRole())
          .build();
    }
    else {
      return Users.builder()
          .email(this.getEmail())
          .password(this.getPassword())
          .name(this.getName())
          .build();
    }
  }
}
