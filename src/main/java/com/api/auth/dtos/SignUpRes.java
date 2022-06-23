package com.api.auth.dtos;

import com.api.common.dtos.CoreRes;
import lombok.Getter;


@Getter
public class SignUpRes extends CoreRes {
  public SignUpRes(boolean ok, String error) {
    super(ok, error);
  }
}
