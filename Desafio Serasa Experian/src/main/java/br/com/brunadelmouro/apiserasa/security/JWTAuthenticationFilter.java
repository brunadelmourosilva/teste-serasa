package br.com.brunadelmouro.apiserasa.security;

import br.com.brunadelmouro.apiserasa.model.User;
import br.com.brunadelmouro.apiserasa.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final UserRepository userRepository;

  private final JwtUtil jwtUtil;

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(
      UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
      throws AuthenticationException {

    try {
      Credentials credentials =
          new ObjectMapper().readValue(req.getInputStream(), Credentials.class);

      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(
              credentials.getEmail(), credentials.getPassword(), new ArrayList<>());

      Authentication auth = authenticationManager.authenticate(authToken);

      return auth;

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth)
      throws IOException, ServletException {

    UsuarioSS usuario = (UsuarioSS) auth.getPrincipal();

    String token = jwtUtil.generateToken(usuario);

    User user = userRepository.findByEmail(usuario.getEmail()).get();

    RetornoLogin retornoLogin = jwtUtil.constroiRetornoLogin(user.getEmail(), token);

    setHttpResponseConfigs(res, retornoLogin);
  }

  private void setHttpResponseConfigs(HttpServletResponse res, RetornoLogin retorno) {

    String json;
    try {
      json = new ObjectMapper().writeValueAsString(retorno);

      res.setStatus(HttpStatus.OK.value());
      res.setContentType("application/json");
      res.setCharacterEncoding("UTF-8");
      res.getWriter().write(json);
      res.flushBuffer();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
