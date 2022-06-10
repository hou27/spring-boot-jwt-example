package demo.api.auth;

import demo.api.jwt.dtos.TokenDto;
import demo.api.user.domain.User;
import demo.api.user.dtos.UserSignInRequest;
import demo.api.user.dtos.UserSignUpRequest;

public interface AuthService {
  /**
   * 유저의 정보로 회원가입
   * @param signUpReq 가입할 유저의 정보 Dto
   * @return 가입된 유저 정보
   */
  User signUp(UserSignUpRequest signUpReq) throws Exception;

  /**
   * 유저 정보로 로그인
   * @param signInReq 유저의 이메일과 비밀번호
   * @return json web token
   */
  TokenDto signIn(UserSignInRequest signInReq);
}
