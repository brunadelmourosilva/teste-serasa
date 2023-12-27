package br.com.brunadelmouro.apiserasa.services;

import br.com.brunadelmouro.apiserasa.consumers.ViaCepConsumer;
import br.com.brunadelmouro.apiserasa.consumers.response.EnderecoResponse;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.enums.ScoreDescricaoEnum;
import br.com.brunadelmouro.apiserasa.mappers.PessoaMapper;
import br.com.brunadelmouro.apiserasa.model.Pessoa;
import br.com.brunadelmouro.apiserasa.repository.PessoaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PessoaService {

  private final ViaCepConsumer viaCepConsumer;
  private final PessoaMapper pessoaMapper;
  private final PessoaRepository pessoaRepository;

  public PessoaResponse criarPessoa(PessoaRequest pessoaRequest) {
    var endereco = viaCepConsumer.obterInformacoesCEP(pessoaRequest.cep());

    var pessoaEntityMapper = pessoaMapper.pessoaRequestToPessoaEntity(pessoaRequest, endereco);

    var scoreDescricao = atualizarScoreDescricao(pessoaEntityMapper.getScore());
    pessoaEntityMapper.setScoreDescricao(scoreDescricao.getScoreDescricao());

    var pessoaCadastrada = pessoaRepository.save(pessoaEntityMapper);

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaCadastrada);
  }

  public Page<PessoaResponse> retornarPessoasPaginadas(
      String nome, Integer idade, String cep, Pageable pageable) {
    var pessoas = pessoaRepository.findByFilters(nome, idade, cep);
    var pessoasResponseMapper = pessoaMapper.pessoaListEntityToPessoaListResponse(pessoas);

    return new PageImpl<>(pessoasResponseMapper, pageable, pessoasResponseMapper.size());
  }

  public PessoaResponse atualizarPessoa(String pessoaId, PessoaRequest pessoaRequest) {
    var pessoaEntity =
        pessoaRepository
            .findById(pessoaId)
            .orElseThrow(RuntimeException::new); // // TODO: 12/27/2023 exception

    var novoEndereco = verificarMudancaCEP(pessoaEntity, pessoaRequest);
    var novoScore = verificarMudancaScore(pessoaEntity, pessoaRequest);

    var pessoaEntityMapper = pessoaMapper.pessoaRequestToPessoaEntity(pessoaRequest, novoEndereco);
    pessoaEntityMapper.setId(pessoaEntity.getId());
    pessoaEntityMapper.setScoreDescricao(novoScore);

    var pessoaEntityResponse = pessoaRepository.save(pessoaEntityMapper);

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaEntityResponse);
  }

  public void deletarPessoa(String pessoaId) {
    pessoaRepository.findById(pessoaId).orElseThrow(RuntimeException::new);

    pessoaRepository.deleteById(pessoaId);
  }

  public PessoaResponse retornarPessoaPorId(String pessoaId) {
    var pessoaEntity =
            pessoaRepository
                    .findById(pessoaId)
                    .orElseThrow(RuntimeException::new); // // TODO: 12/27/2023 exception

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaEntity);
  }

  private ScoreDescricaoEnum atualizarScoreDescricao(Integer score) {
    if (score >= 0 && score <= 200) {
      return ScoreDescricaoEnum.INSUFICIENTE;
    } else if (score >= 201 && score <= 500) {
      return ScoreDescricaoEnum.INACEITAVEL;
    } else if (score >= 501 && score <= 700) {
      return ScoreDescricaoEnum.ACEITAVEL;
    } else {
      return ScoreDescricaoEnum.RECOMENDAVEL;
    }
  }

  private EnderecoResponse verificarMudancaCEP(Pessoa pessoaEntity, PessoaRequest pessoaRequest) {
    if (!pessoaRequest.cep().equals(pessoaEntity.getCep())) {
      return viaCepConsumer.obterInformacoesCEP(pessoaRequest.cep());
    }

    return new EnderecoResponse(
        pessoaEntity.getCep(),
        pessoaEntity.getEstado(),
        pessoaEntity.getCidade(),
        pessoaEntity.getBairro(),
        pessoaEntity.getLogradouro());
  }

  private String verificarMudancaScore(Pessoa pessoaEntity, PessoaRequest pessoaRequest) {
    if (!pessoaRequest.score().equals(pessoaEntity.getScore())) {
      return atualizarScoreDescricao(pessoaRequest.score()).getScoreDescricao();
    }

    return pessoaEntity.getScoreDescricao();
  }
}
