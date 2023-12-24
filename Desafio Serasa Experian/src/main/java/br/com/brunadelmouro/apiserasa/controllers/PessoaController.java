package br.com.brunadelmouro.apiserasa.controllers;

import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.services.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

  private final PessoaService pessoaService;

  @PostMapping
  public ResponseEntity<PessoaResponse> criarPessoa(@RequestBody PessoaRequest pessoaRequest) {

    return new ResponseEntity<>(pessoaService.criarPessoa(pessoaRequest), HttpStatus.CREATED);
  }
}
