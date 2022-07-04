package com.api.user;

import com.api.user.domain.Users;
import java.util.List;
import java.util.Optional;

public interface UserService {
  /**
   * 모든 유저 리스트를 반환
   * @return 유저 리스트
   */
  List<Users> findAll();

  /**
   * 이메일을 통해 유저 조회
   * @param email
   * @return 조회된 유저
   */
  Optional<Users> findByEmail(String email);

  /**
   * 이름을 통해 유저 조회
   * @param name
   * @return 조회된 유저
   */
  Optional<Users> findByName(String name);

  /**
   * 유저 정보 수정
   * @param user 수정활 User Entity
   * @param newInfo
   * @return 수정된 User
   */
  Users updateUser(Users user, String newInfo);
}
