package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignInRequest;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder bCryptPasswordEncoder;

  @Override
  public User signUp(UserSignUpRequest signUpReq) throws Exception {
    System.out.println("signUpReq = " + signUpReq.toString());
    if(this.isEmailExist(signUpReq.getEmail())) {
      throw new Exception("Your Mail already Exist.");
    }
    User newUser = signUpReq.toUserEntity();
    newUser.hashPassword(bCryptPasswordEncoder);
    return userRepository.save(newUser);
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
