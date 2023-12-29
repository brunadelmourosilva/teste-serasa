package br.com.brunadelmouro.apiserasa.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetornoLogin {

  private String email;
  private String token;
}
