package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
//  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Override
  public User signUp(User user) {
//    user.hashPassword(passwordEncoder);
    return userRepository.save(user);
  }

  @Override
  public User findUserByEmail(String email) {
    return null;
  }

  @Override
  public User updateUser(User user, String newInfo) {
    return null;
  }

  @Override
  public boolean isEmailExist(String email) {
    return false;
  }
}
