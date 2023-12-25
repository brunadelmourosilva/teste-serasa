package br.com.brunadelmouro.apiserasa.controllers;

import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.services.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

  private final PessoaService pessoaService;

  @PostMapping
  public ResponseEntity<PessoaResponse> criarPessoa(@RequestBody PessoaRequest pessoaRequest) {

    return new ResponseEntity<>(pessoaService.criarPessoa(pessoaRequest), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<Page<PessoaResponse>> retornarPessoasPaginadas(
      @RequestParam(required = false) String nome,
      @RequestParam(required = false) Integer idade,
      @RequestParam(required = false) String cep,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return new ResponseEntity<>(
        pessoaService.retornarPessoasPaginadas(nome, idade, cep, pageable), HttpStatus.OK);
  }
}
