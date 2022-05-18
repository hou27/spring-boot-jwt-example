package demo.api.user.dtos;

import demo.api.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class UserSignUpRequest {
  @NotEmpty(message = "Please enter your Email")
  @Email
  private String email;
  @NotEmpty(message = "Please enter your Password")
  private String password;
  @NotEmpty(message = "Please enter your Name")
  private String name;

  @Builder
  public UserSignUpRequest(String email, String password, String name) {
    this.email = email;
    this.password = password;
    this.name = name;
  }

  /**
   * Transform to User Entity
   * @return User Entity
   */
  public User toUserEntity() {
    return User.builder()
        .email(this.getEmail())
        .password(this.getPassword())
        .name(this.getName())
        .build();
  }
}
