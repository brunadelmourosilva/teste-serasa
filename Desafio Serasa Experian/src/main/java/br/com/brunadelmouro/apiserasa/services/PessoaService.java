package br.com.brunadelmouro.apiserasa.services;

import br.com.brunadelmouro.apiserasa.consumers.ViaCepConsumer;
import br.com.brunadelmouro.apiserasa.consumers.response.EnderecoResponse;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
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
    var pessoaEntity = pessoaMapper.pessoaRequestToPessoaEntity(pessoaRequest, endereco);
    var pessoaCadastrada = pessoaRepository.save(pessoaEntity);

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaCadastrada);
  }

  public Page<PessoaResponse> retornarPessoasPaginadas(
      String nome, Integer idade, String cep, Pageable pageable) {

    var pessoas = pessoaRepository.findByFilters(nome, idade, cep);
    var pessoasResponse = pessoaMapper.pessoaListEntityToPessoaListResponse(pessoas);

    return new PageImpl<>(pessoasResponse, pageable, pessoasResponse.size());
  }

  public PessoaResponse atualizarPessoa(String pessoaId, PessoaRequest pessoaRequest) {

    var pessoaEntity =
        pessoaRepository
            .findById(pessoaId)
            .orElseThrow(RuntimeException::new); // // TODO: 12/27/2023 exception

    var novoEndereco = verificarMudancaCEP(pessoaEntity, pessoaRequest);

    var pessoaEntityAtualizada =
        pessoaMapper.pessoaRequestToPessoaEntity(pessoaRequest, novoEndereco);
    pessoaEntityAtualizada.setId(pessoaEntity.getId());

    var pessoaEntityResponse = pessoaRepository.save(pessoaEntityAtualizada);

    //// TODO: 12/27/2023 scoreDescricao

    return pessoaMapper.pessoaEntityToPessoaResponse(pessoaEntityResponse);
  }

  public void deletarPessoa(String pessoaId) {
    pessoaRepository.findById(pessoaId).orElseThrow(RuntimeException::new);

    pessoaRepository.deleteById(pessoaId);
  }

  //// TODO: 12/27/2023 endpoint por id get

  //// TODO: 12/27/2023 scoreDescricao salvar na entidade, e mudar ep update...

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
}
