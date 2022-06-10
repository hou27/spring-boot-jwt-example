package demo.api.user;

import demo.api.user.domain.User;
import demo.api.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<User> findByName(String name) {
    return userRepository.findByName(name);
  }

//  @Override
//  public Optional<User> getMyInfo() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String username = null;
//
//    if (authentication == null) {
//      log.debug("Security Context에 인증 정보가 없습니다.");
//      return Optional.empty();
//    }
//
//    if (authentication.getPrincipal() instanceof UserDetails) {
//      UserDetails springSecurityUserInfo = (UserDetails) authentication.getPrincipal();
//      username = springSecurityUserInfo.getUsername();
//    } else if (authentication.getPrincipal() instanceof String) {
//      username = (String) authentication.getPrincipal();
//    }
//
//    return Optional.ofNullable(userRepository.findByName(username).orElse(null));
//  }

  @Override
  public User updateUser(User user, String newInfo) {
    return null;
  }
}
