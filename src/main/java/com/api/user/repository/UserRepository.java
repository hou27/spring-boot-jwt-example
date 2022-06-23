package com.api.user.repository;

import com.api.user.domain.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByEmail(String email);

  Optional<Users> findByName(String name);

  /**
   * 이메일 중복 여부를 확인
   *
   * @param email
   * @return true | false
   */
  boolean existsByEmail(String email);
}
