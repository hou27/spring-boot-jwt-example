package com.api.config;

import com.api.user.domain.Users;
import com.api.exception.UserNotFoundException;
import com.api.user.repository.UserRepository;
import java.util.Collections;
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
    System.out.println("email in loadUserByUsername = " + email);
    Users user = userRepository.findByEmail(email)
        .orElseThrow(UserNotFoundException::new);

    Set<GrantedAuthority> grantedAuthorities = Collections.singleton(user.getRole());

    return new org
        .springframework
        .security
        .core
        .userdetails
        .User(user.getEmail(), user.getPassword(), grantedAuthorities);
  }
}