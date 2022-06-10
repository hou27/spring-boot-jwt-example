package demo.api.user.repository;

import demo.api.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  /**
   * 이메일 중복 여부를 확인
   *
   * @param email
   * @return true | false
   */
  boolean existsByEmail(String email);
}
