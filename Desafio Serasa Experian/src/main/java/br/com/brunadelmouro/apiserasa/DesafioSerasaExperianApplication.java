package br.com.brunadelmouro.apiserasa;

import br.com.brunadelmouro.apiserasa.enums.RoleEnum;
import br.com.brunadelmouro.apiserasa.model.Role;
import br.com.brunadelmouro.apiserasa.model.User;
import br.com.brunadelmouro.apiserasa.repository.RoleRepository;
import br.com.brunadelmouro.apiserasa.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("!test")
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

    Role roleAdmin =
        Role.builder().roleName("ROLE_".concat(RoleEnum.ADMIN.name())).user(admin).build();

    Role roleUser =
        Role.builder().roleName("ROLE_".concat(RoleEnum.USER.name())).user(user).build();

    roleRepository.saveAll(List.of(roleAdmin, roleUser));
  }
}
