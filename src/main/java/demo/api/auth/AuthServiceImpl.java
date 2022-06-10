package demo.api.auth;

import demo.api.exception.CustomException;
import demo.api.jwt.JwtTokenFilter;
import demo.api.jwt.JwtTokenProvider;
import demo.api.jwt.dtos.TokenDto;
import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignInRequest;
import demo.api.user.dtos.UserSignUpRequest;
import demo.api.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @Override
  @Transactional
  public User signUp(UserSignUpRequest signUpReq) throws Exception {
    System.out.println("signUpReq = " + signUpReq.toString());

    if(userRepository.existsByEmail(signUpReq.getEmail())) {
      throw new Exception("Your Mail already Exist.");
    }
    User newUser = signUpReq.toUserEntity();
    newUser.hashPassword(bCryptPasswordEncoder);
    return userRepository.save(newUser);
  }

  @Override
  public ResponseEntity<TokenDto> signIn(UserSignInRequest signInReq) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              signInReq.getEmail(),
              signInReq.getPassword()
          )
      );
      TokenDto tokenDto = new TokenDto(jwtTokenProvider.generateToken(authentication));

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccess_token());

      return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid credentials supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }
}
