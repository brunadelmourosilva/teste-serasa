package br.com.brunadelmouro.apiserasa.consumers;

import br.com.brunadelmouro.apiserasa.consumers.response.EnderecoResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ViaCepConsumer {

  @Headers("Content-Type: application/json")
  @RequestLine("GET /ws/{cep}/json")
  EnderecoResponse obterInformacoesCEP(@Param("cep") String cep);
}
