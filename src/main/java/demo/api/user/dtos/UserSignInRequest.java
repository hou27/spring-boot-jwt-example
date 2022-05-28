package demo.api.user.dtos;

import demo.api.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class UserSignInRequest {
  @NotEmpty(message = "Please enter your Email")
  @Email
  private String email;

  @NotEmpty(message = "Please enter your Password")
  private String password;
}
