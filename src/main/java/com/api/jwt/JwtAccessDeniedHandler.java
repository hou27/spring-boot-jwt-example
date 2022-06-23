package com.api.jwt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * AccessDeniedHandler
 *
 * AuthenticationEntryPoint와 달리 AccessDeniedHandler는
 * 유저 정보는 있으나, 엑세스 권한이 없는 경우 동작하는 친구이다.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
    response.sendError(HttpServletResponse.SC_FORBIDDEN);
  }
}
