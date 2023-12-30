package br.com.brunadelmouro.apiserasa.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import br.com.brunadelmouro.apiserasa.consumers.ViaCepConsumer;
import br.com.brunadelmouro.apiserasa.consumers.response.EnderecoResponse;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.exceptions.GenericException;
import br.com.brunadelmouro.apiserasa.exceptions.NotFoundException;
import br.com.brunadelmouro.apiserasa.mappers.PessoaMapper;
import br.com.brunadelmouro.apiserasa.model.Pessoa;
import br.com.brunadelmouro.apiserasa.repository.PessoaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class PessoaServiceTest {

  @InjectMocks private PessoaService pessoaService;

  @Mock private ViaCepConsumer viaCepConsumer;

  @Mock private PessoaMapper pessoaMapper;

  @Mock private PessoaRepository pessoaRepository;

  private EnderecoResponse mockEnderecoResponse;
  private PessoaRequest mockPessoaRequest;
  private Pessoa mockPessoa;
  private PessoaResponse mockPessoaResponse;

  @BeforeEach
  void setUp() {
    mockEnderecoResponse = buildEnderecoResponseObject();
    mockPessoaRequest = buildPessoaRequestObject();
    mockPessoa = buildPessoaEntityObject();
    mockPessoaResponse = buildPessoaResponseObject();
  }

  @Test
  public void criarPessoaShouldReturnACorrectResponseWhenACorrectBodyRequestIsPassed() {

    when(viaCepConsumer.obterInformacoesCEP(anyString())).thenReturn(mockEnderecoResponse);
    when(pessoaMapper.pessoaRequestToPessoaEntity(any(), any())).thenReturn(mockPessoa);
    when(pessoaRepository.save(any())).thenReturn(mockPessoa);
    when(pessoaMapper.pessoaEntityToPessoaResponse(any())).thenReturn(mockPessoaResponse);

    var response = pessoaService.criarPessoa(mockPessoaRequest);

    assertNotNull(response);
  }

  @Test
  public void criarPessoaShouldThrowsAGenericExceptionWhenTheExternalCallThrowsAnError() {

    when(viaCepConsumer.obterInformacoesCEP(anyString())).thenThrow(new RuntimeException());

    var exceptionResponse =
        assertThrows(GenericException.class, () -> pessoaService.criarPessoa(mockPessoaRequest));

    assertTrue(
        exceptionResponse
            .getMessage()
            .contains(
                "Um erro inesperado ocorreu enquanto a chamada para a API de CEP foi feita: "));
  }

  @Test
  public void retornarPessoasPaginadasShouldReturnACorrectPagedList() {

    when(pessoaRepository.findByFilters(null, null, null)).thenReturn(List.of(mockPessoa));
    when(pessoaMapper.pessoaListEntityToPessoaListResponse(anyList()))
        .thenReturn(List.of(mockPessoaResponse));

    var response = pessoaService.retornarPessoasPaginadas(null, null, null, PageRequest.of(0, 10));

    assertEquals(1L, response.getTotalElements());
    assertEquals(1L, response.getTotalPages());
    assertNotNull(response.getContent());
  }

  @Test
  public void atualizarPessoaShouldUpdateTheObjectWhenACorrectRequestIsPassed() {

    when(pessoaRepository.findById(mockPessoa.getId())).thenReturn(Optional.of(mockPessoa));
    when(pessoaMapper.pessoaRequestToPessoaEntity(any(), any())).thenReturn(mockPessoa);
    when(pessoaRepository.save(any())).thenReturn(mockPessoa);
    when(pessoaMapper.pessoaEntityToPessoaResponse(any())).thenReturn(mockPessoaResponse);

    var response = pessoaService.atualizarPessoa(mockPessoa.getId(), mockPessoaRequest);

    assertNotNull(response);
  }

  @Test
  public void atualizarPessoaShouldThrowsANotFoundExceptionWhenTheObjectWasNotFound() {

    when(pessoaRepository.findById(anyString()))
        .thenThrow(new NotFoundException("Pessoa não encontrada."));

    var exceptionResponse =
        assertThrows(
            NotFoundException.class,
            () -> pessoaService.atualizarPessoa(anyString(), mockPessoaRequest));

    assertTrue(exceptionResponse.getMessage().contains("Pessoa não encontrada."));
  }

  @Test
  public void deletarPessoaShouldDeleteTheObjectWhenTheCorrectIdIsPassed() {

    when(pessoaRepository.findById(mockPessoa.getId())).thenReturn(Optional.of(mockPessoa));
    doNothing().when(pessoaRepository).deleteById(mockPessoa.getId());

    pessoaService.deletarPessoa(mockPessoa.getId());
  }

  private EnderecoResponse buildEnderecoResponseObject() {
    return new EnderecoResponse(
        "37500190", "MG", "Itajubá", "Pinheirinho", "Rua Deputado Aureliano Chaves");
  }

  private PessoaRequest buildPessoaRequestObject() {
    return new PessoaRequest("Bruna", 21, "35999999999", "37500190", 190);
  }

  private Pessoa buildPessoaEntityObject() {
    return Pessoa.builder()
        .nome("Bruna")
        .cep("37500190")
        .cidade("Itajubá")
        .bairro("Pinheirinho")
        .estado("MG")
        .logradouro("Rua Deputado Aureliano Chaves")
        .idade(21)
        .telefone("35999999999")
        .score(100)
        .build();
  }

  private PessoaResponse buildPessoaResponseObject() {
    return new PessoaResponse(
        "1",
        "Bruna",
        21,
        "35999999999",
        100,
        "Insuficiente",
        "37500190",
        "MG",
        "Itajubá",
        "Pinheirinho",
        "Rua Deputado Aureliano Chaves");
  }
}
