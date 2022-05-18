package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public User signUp(UserSignUpRequest signUpReq) throws Exception {
    if(this.isEmailExist(signUpReq.getEmail())) {
      throw new Exception("Your Mail already Exist.");
    }
    return userRepository.save(signUpReq.toUserEntity());
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public User updateUser(User user, String newInfo) {
    return null;
  }

  /**
   * 이메일 중복 여부를 확인
   *
   * @param email
   * @return true | false
   */
  private boolean isEmailExist(String email) {
    Optional<User> byEmail = userRepository.findByEmail(email);
    return !byEmail.isEmpty();
  }
}
