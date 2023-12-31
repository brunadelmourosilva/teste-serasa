package br.com.brunadelmouro.apiserasa.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.model.Pessoa;
import br.com.brunadelmouro.apiserasa.services.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PessoaController.class)
@Import(PessoaController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PessoaControllerTest {

  public static final String MOCK_PESSOA_ID = "1";
  @Autowired private MockMvc mockMvc;

  @MockBean private PessoaService pessoaService;

  @Autowired private WebApplicationContext webApplicationContext;

  @BeforeEach()
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  void criarPessoaShouldReturnCreatedStatus() throws Exception {
    PessoaRequest mockPessoaRequest = buildPessoaRequestObject();
    PessoaResponse mockPessoaResponse = buildPessoaResponseObject();
    String json = new ObjectMapper().writeValueAsString(mockPessoaRequest);

    when(pessoaService.criarPessoa(any())).thenReturn(mockPessoaResponse);

    var request =
        MockMvcRequestBuilders.post("/pessoas")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(json);

    mockMvc
        .perform(request)
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1"));
  }

  @Test
  void retornarPessoasPaginadasShouldReturnOkStatus() throws Exception {

    when(pessoaService.retornarPessoasPaginadas(any(), any(), any(), any()))
        .thenReturn(new PageImpl<>(List.of(buildPessoaResponseObject())));

    var request =
        MockMvcRequestBuilders.get("/pessoas")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());
  }

  @Test
  void atualizarPessoaShouldReturnOkStatus() throws Exception {
    PessoaRequest mockPessoaRequest = buildPessoaRequestObject();
    PessoaResponse mockPessoaResponse = buildPessoaResponseObject();
    String json = new ObjectMapper().writeValueAsString(mockPessoaRequest);

    when(pessoaService.atualizarPessoa(any(), any())).thenReturn(mockPessoaResponse);

    var request =
        MockMvcRequestBuilders.put("/pessoas/{pessoaId}", MOCK_PESSOA_ID)
            .param("page", "0")
            .param("size", "10")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1"));
  }

  @Test
  void deletarPessoaShouldReturnNoContentStatus() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.delete("/pessoas/{pessoaId}", MOCK_PESSOA_ID))
        .andExpect(status().isNoContent());
  }

  @Test
  void retornarPessoaPorIdShouldReturnOkStatus() throws Exception {
    PessoaResponse mockPessoaResponse = buildPessoaResponseObject();

    when(pessoaService.retornarPessoaPorId(anyString())).thenReturn(mockPessoaResponse);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/pessoas/{pessoaId}", MOCK_PESSOA_ID))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"));
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
