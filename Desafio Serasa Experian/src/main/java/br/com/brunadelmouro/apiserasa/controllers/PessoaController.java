package br.com.brunadelmouro.apiserasa.controllers;

import br.com.brunadelmouro.apiserasa.annotations.NoHyphen;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.services.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
  public ResponseEntity<PessoaResponse> criarPessoa(@Valid @RequestBody PessoaRequest pessoaRequest) {

    return new ResponseEntity<>(pessoaService.criarPessoa(pessoaRequest), HttpStatus.CREATED);
  }

  @Operation(summary = "Obter lista paginada de pessoas por filtros")
  @GetMapping
  public ResponseEntity<Page<PessoaResponse>> retornarPessoasPaginadas(
      @RequestParam(value = "nome", required = false) String nome,
      @RequestParam(value = "idade", required = false) Integer idade,
      @RequestParam(value = "cep", required = false) @NoHyphen String cep,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return new ResponseEntity<>(
        pessoaService.retornarPessoasPaginadas(nome, idade, cep, pageable), HttpStatus.OK);
  }

  @Operation(summary = "Atualizar dados de cadastro da pessoa")
  @PutMapping("/{pessoaId}")
  public ResponseEntity<PessoaResponse> atualizarPessoa(
      @PathVariable String pessoaId, @RequestBody PessoaRequest pessoaRequest) {

    return new ResponseEntity<>(
        pessoaService.atualizarPessoa(pessoaId, pessoaRequest), HttpStatus.OK);
  }

  @Operation(summary = "Deletar pessoa por id")
  @DeleteMapping("/{pessoaId}")
  public ResponseEntity<Void> deletarPessoa(@PathVariable String pessoaId) {

    pessoaService.deletarPessoa(pessoaId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(summary = "Obter pessoa por id")
  @GetMapping("/{pessoaId}")
  public ResponseEntity<PessoaResponse> retornarPessoaPorId(@PathVariable String pessoaId) {

    return new ResponseEntity<>(pessoaService.retornarPessoaPorId(pessoaId), HttpStatus.OK);
  }
}
