package demo.api.auth;

import demo.api.auth.dtos.SignUpRes;
import demo.api.exception.CustomException;
import demo.api.jwt.JwtTokenProvider;
import demo.api.jwt.dtos.RegenerateTokenDto;
import demo.api.jwt.dtos.TokenDto;
import demo.api.user.domain.User;
import demo.api.auth.dtos.SignInReq;
import demo.api.auth.dtos.SignUpReq;
import demo.api.user.repository.UserRepository;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
  private final RedisTemplate<String, String> redisTemplate;

  @Value("${jwt.token.refresh-token-expire-length}")
  private long refresh_token_expire_time;

  @Override
  @Transactional
  public SignUpRes signUp(SignUpReq signUpReq){
    System.out.println("signUpReq = " + signUpReq.toString());

    if(userRepository.existsByEmail(signUpReq.getEmail())) {
      return new SignUpRes(false, "Your Mail already Exist.");
    }
    User newUser = signUpReq.toUserEntity();
    newUser.hashPassword(bCryptPasswordEncoder);

    User user = userRepository.save(newUser);
    if(!Objects.isNull(user)) {
      return new SignUpRes(true, null);
    }
    return new SignUpRes(false, "Fail to Sign Up");
  }

  @Override
  public ResponseEntity<TokenDto> signIn(SignInReq signInReq) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              signInReq.getEmail(),
              signInReq.getPassword()
          )
      );

      String refresh_token = jwtTokenProvider.generateRefreshToken(authentication);

      TokenDto tokenDto = new TokenDto(
          jwtTokenProvider.generateAccessToken(authentication),
          refresh_token
      );

      // Redis??? ?????? - ?????? ?????? ????????? ?????? ?????? ?????? ??????
      redisTemplate.opsForValue().set(
              authentication.getName(),
              refresh_token,
              refresh_token_expire_time,
              TimeUnit.MILLISECONDS
          );

      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add("Authorization", "Bearer " + tokenDto.getAccess_token());

      return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid credentials supplied", HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<TokenDto> regenerateToken(RegenerateTokenDto refreshTokenDto) {
    String refresh_token = refreshTokenDto.getRefresh_token();
    try {
      // Refresh Token ??????
      if (!jwtTokenProvider.validateRefreshToken(refresh_token)) {
        throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
      }

      // Access Token ?????? User email??? ????????????.
      Authentication authentication = jwtTokenProvider.getAuthenticationByRefreshToken(refresh_token);

      // Redis?????? ????????? Refresh Token ?????? ????????????.
      String refreshToken = redisTemplate.opsForValue().get(authentication.getName());
      if(!refreshToken.equals(refresh_token)) {
        throw new CustomException("Refresh Token doesn't match.", HttpStatus.BAD_REQUEST);
      }

      // ?????? ?????????
      String new_refresh_token = jwtTokenProvider.generateRefreshToken(authentication);
      TokenDto tokenDto = new TokenDto(
          jwtTokenProvider.generateAccessToken(authentication),
          new_refresh_token
      );

      // RefreshToken Redis??? ????????????
      redisTemplate.opsForValue().set(
          authentication.getName(),
          new_refresh_token,
          refresh_token_expire_time,
          TimeUnit.MILLISECONDS
      );

      HttpHeaders httpHeaders = new HttpHeaders();

      return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid refresh token supplied", HttpStatus.BAD_REQUEST);
    }
  }
}
