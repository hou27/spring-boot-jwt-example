package demo.api.auth.dtos;

import demo.api.common.dtos.CoreRes;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
public class SignUpRes extends CoreRes {
  public SignUpRes(boolean ok, String error) {
    super(ok, error);
  }
}
