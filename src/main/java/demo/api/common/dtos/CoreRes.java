package demo.api.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoreRes {
  private boolean ok;
  private String error;
}
