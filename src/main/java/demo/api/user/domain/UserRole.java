package demo.api.user.domain;

import lombok.Getter;

@Getter
public enum UserRole {
  ROLE_USER // Spring Security의 role 네이밍 규칙 : ROLE_권한이름
}
