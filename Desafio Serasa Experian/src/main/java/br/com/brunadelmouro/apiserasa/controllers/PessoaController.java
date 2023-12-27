package br.com.brunadelmouro.apiserasa.controllers;

import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pessoas Controller")
@AllArgsConstructor
@RestController
@RequestMapping("/pessoas")
public class PessoaController {

  private final PessoaService pessoaService;

  @Operation(summary = "Cadastrar pessoa")
  @PostMapping
  public ResponseEntity<PessoaResponse> criarPessoa(@RequestBody PessoaRequest pessoaRequest) {

    return new ResponseEntity<>(pessoaService.criarPessoa(pessoaRequest), HttpStatus.CREATED);
  }

  @Operation(summary = "Obter lista paginada de pessoas por filtros")
  @GetMapping
  public ResponseEntity<Page<PessoaResponse>> retornarPessoasPaginadas(
      @RequestParam(value = "nome", required = false) String nome,
      @RequestParam(value = "idade", required = false) Integer idade,
      @RequestParam(value = "cep", required = false) String cep,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return new ResponseEntity<>(
        pessoaService.retornarPessoasPaginadas(nome, idade, cep, pageable), HttpStatus.OK);
  }
}
