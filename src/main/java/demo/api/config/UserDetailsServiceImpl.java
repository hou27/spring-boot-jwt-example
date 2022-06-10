package demo.api.config;

import demo.api.user.domain.User;
import demo.api.user.exception.UserNotFoundException;
import demo.api.user.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UserNotFoundException {

    User user = userRepository.findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

    return new org
        .springframework
        .security
        .core
        .userdetails
        .User(user.getEmail(), user.getPassword(), grantedAuthorities);
  }
}