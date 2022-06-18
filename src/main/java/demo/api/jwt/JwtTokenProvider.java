package demo.api.jwt;

import demo.api.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

// 유저 정보로 JWT 토큰을 만들거나 토큰을 바탕으로 유저 정보를 가져옴
@Component
public class JwtTokenProvider {
  @Value("${jwt.token.access-token-secret-key}")
  private String access_token_secret_key;

  @Value("${jwt.token.refresh-token-secret-key}")
  private String refresh_token_secret_key;

  @Value("${jwt.token.access-token-expire-length}")
  private long access_token_expire_time;

  @Value("${jwt.token.refresh-token-expire-length}")
  private long refresh_token_expire_time;

  @Autowired
  private UserDetailsService userDetailsService;

  /**
   * 적절한 설정을 통해 Access 토큰을 생성하여 반환
   * @param authentication
   * @return access token
   */
  public String generateAccessToken(Authentication authentication) {
    Claims claims = Jwts.claims().setSubject(authentication.getName());
//    claims.put("auth", appUserRoles.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));

    Date now = new Date();
    Date expiresIn = new Date(now.getTime() + access_token_expire_time);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiresIn)
        .signWith(SignatureAlgorithm.HS256, access_token_secret_key)
        .compact();
  }

  /**
   * 적절한 설정을 통해 Refresh 토큰을 생성하여 반환
   * @param authentication
   * @return refresh token
   */
  public String generateRefreshToken(Authentication authentication) {
    Claims claims = Jwts.claims().setSubject(authentication.getName());

    Date now = new Date();
    Date expiresIn = new Date(now.getTime() + refresh_token_expire_time);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiresIn)
        .signWith(SignatureAlgorithm.HS256, refresh_token_secret_key)
        .compact();
  }

  /**
   * Access 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
   * @param access_token
   * @return
   */
  public Authentication getAuthenticationByAccessToken(String token) {
    String username = Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(token).getBody().getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * Refresh 토큰으로부터 클레임을 만들고, 이를 통해 User 객체를 생성하여 Authentication 객체를 반환
   * @param refresh_token
   * @return
   */
  public Authentication getAuthenticationByRefreshToken(String token) {
    String username = Jwts.parser().setSigningKey(refresh_token_secret_key).parseClaimsJws(token).getBody().getSubject();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  /**
   * http 헤더로부터 bearer 토큰을 가져옴.
   * @param req
   * @return
   */
  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  /**
   * Access 토큰을 검증
   * @param token
   * @return
   */
  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser().setSigningKey(access_token_secret_key).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
      throw new CustomException("Error on Access Token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Refresh 토큰을 검증
   * @param token
   * @return
   */
  public boolean validateRefreshToken(String token) {
    try {
      Jwts.parser().setSigningKey(refresh_token_secret_key).parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      // MalformedJwtException | ExpiredJwtException | IllegalArgumentException
      throw new CustomException("Error on Refresh Token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
