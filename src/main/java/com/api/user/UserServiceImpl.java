package com.api.user;

import com.api.user.repository.UserRepository;
import com.api.user.domain.Users;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Override
  public List<Users> findAll() {
    List<Users> usersList = userRepository.findAll();
    usersList.stream().forEach(user -> user.setPassword(""));

    for (Users user : usersList) {
      System.out.println("user = " + user.toString());
    }
    return usersList;
  }

  @Override
  public Optional<Users> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<Users> findByName(String name) {
    return userRepository.findByName(name);
  }

  @Override
  public Users updateUser(Users user, String newInfo) {
    return null;
  }
}
