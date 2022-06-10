package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import java.util.List;
import java.util.Optional;

public interface UserService {
  /**
   * 모든 유저 리스트를 반환
   * @return 유저 리스트
   */
  List<User> findAll();

  /**
   * 이메일을 통해 유저 조회
   * @param email
   * @return 조회된 유저
   */
  Optional<User> findByEmail(String email);

  /**
   * 유저 정보 수정
   * @param user 수정활 User Entity
   * @param newInfo
   * @return 수정된 User
   */
  User updateUser(User user, String newInfo);
}
