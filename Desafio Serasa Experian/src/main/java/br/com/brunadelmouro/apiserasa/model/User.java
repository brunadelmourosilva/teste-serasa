package br.com.brunadelmouro.apiserasa.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "USERS")
public class User {

  @Column(name = "USER_ID")
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "CREATION_DATE")
  private OffsetDateTime creationDate = OffsetDateTime.now();

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  private List<Role> roles;

  public List<GrantedAuthority> rolesAsGrantedAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
        .collect(Collectors.toList());
  }
}
