package demo.api.user;

import demo.api.user.domain.User;

public interface UserService {
  /**
   * 유저의 정보로 회원가입
   * @param user 가입할 유저의 정보
   * @return 가입된 유저 정보
   */
  User signUp(User user);

  /**
   * 이메일을 통해 유저 조회
   * @param email
   * @return 조회된 유저
   */
  User findUserByEmail(String email);

  /**
   * 유저 정보 수정
   * @param user 수정활 User Entity
   * @param newInfo
   * @return 수정된 User
   */
  User updateUser(User user, String newInfo);

  /**
   * 이메일 중복 여부를 확인
   * @param email
   * @return true | false
   */
  boolean isEmailExist(String email);
}
