package com.api.user.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserRole implements GrantedAuthority {
  ROLE_CLIENT, // Spring Security의 role 네이밍 규칙 : ROLE_권한이름
  ROLE_ADMIN;

  @Override
  public String getAuthority() {
    return null;
  }
}
