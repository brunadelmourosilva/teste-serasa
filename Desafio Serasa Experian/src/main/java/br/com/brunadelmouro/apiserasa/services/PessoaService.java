package br.com.brunadelmouro.apiserasa.services;

import br.com.brunadelmouro.apiserasa.consumers.ViaCepConsumer;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.mappers.PessoaMapper;
import br.com.brunadelmouro.apiserasa.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PessoaService {

  private final ViaCepConsumer viaCepConsumer;
  private final PessoaMapper pessoaMapper;
  private final PessoaRepository pessoaRepository;

  public PessoaResponse criarPessoa(PessoaRequest pessoaRequest) {
    var endereco = viaCepConsumer.obterInformacoesCEP(pessoaRequest.cep());
    var pessoaEntity = pessoaMapper.pessoaRequestToPessoaEntity(pessoaRequest, endereco);
    var pessoaCadastrada = pessoaRepository.save(pessoaEntity);

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaCadastrada);
  }
}
