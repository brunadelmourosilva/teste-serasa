package br.com.brunadelmouro.apiserasa.security;

import br.com.brunadelmouro.apiserasa.model.User;
import br.com.brunadelmouro.apiserasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email).get();

    return new UsuarioSS(
        user.getId(), user.getEmail(), user.getPassword(), user.rolesAsGrantedAuthorities());
  }
}
