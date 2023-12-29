package br.com.brunadelmouro.apiserasa;

import br.com.brunadelmouro.apiserasa.model.Role;
import br.com.brunadelmouro.apiserasa.model.User;
import br.com.brunadelmouro.apiserasa.repository.RoleRepository;
import br.com.brunadelmouro.apiserasa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class DesafioSerasaExperianApplication implements CommandLineRunner {

  @Autowired private RoleRepository roleRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(DesafioSerasaExperianApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    User admin =
        User.builder()
            .name("Admin")
            .email("admin@gmail.com")
            .password(passwordEncoder.encode("123"))
            .build();

    User user =
        User.builder()
            .name("User")
            .email("user@gmail.com")
            .password(passwordEncoder.encode("123"))
            .build();

    userRepository.saveAll(List.of(admin, user));

    //// TODO: 12/28/2023 MANY TO MANY
    Role roleAdmin = Role.builder().roleName("ADMIN").user(admin).build();

    Role roleUser = Role.builder().roleName("USER").user(user).build();

    roleRepository.saveAll(List.of(roleAdmin, roleUser));
  }
}
