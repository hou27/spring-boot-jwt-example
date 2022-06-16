package demo.api.user.domain;

import demo.api.common.domain.CoreEntity;
import demo.api.user.repository.UserRepository;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Entity 어노테이션을 클래스에 선언하면 그 클래스는 JPA가 관리
@Entity
@Getter @Setter
@NoArgsConstructor
@ToString
public class User extends CoreEntity {
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false)
  private String password;
  @Column(length = 10, nullable = false, unique = true)
  private String name;

//  @Enumerated(EnumType.STRING)
//  private UserRole role;

  @Builder
  public User(String email, String password, String name /*UserRole role*/) {
    this.email = email;
    this.password = password;
    this.name = name;
//    this.role = role;
  }

  // https://reflectoring.io/spring-security-password-handling/
  /**
   * 비밀번호를 암호화
   * @param passwordEncoder 암호화 할 인코더 클래스
   * @return 변경된 유저 Entity
   */
  public User hashPassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
    return this;
  }

  /**
   * 비밀번호 확인
   * @param plainPassword 암호화 이전의 비밀번호
   * @param passwordEncoder 암호화에 사용된 클래스
   * @return true | false
   */
  public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(plainPassword, this.password);
  }
}
