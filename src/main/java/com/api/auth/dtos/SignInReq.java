package com.api.auth.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignInReq {
  @NotEmpty(message = "Please enter your Email")
  @Email
  private String email;
  @NotEmpty(message = "Please enter your Password")
  private String password;

  @Builder
  public SignInReq(String email, String password) {
    this.email = email;
    this.password = password;
  }
}

