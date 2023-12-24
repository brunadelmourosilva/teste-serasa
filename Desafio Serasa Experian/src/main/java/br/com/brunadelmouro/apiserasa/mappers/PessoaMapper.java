package br.com.brunadelmouro.apiserasa.mappers;

import br.com.brunadelmouro.apiserasa.consumers.response.EnderecoResponse;
import br.com.brunadelmouro.apiserasa.dto.request.PessoaRequest;
import br.com.brunadelmouro.apiserasa.dto.response.PessoaResponse;
import br.com.brunadelmouro.apiserasa.model.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PessoaMapper {

    @Mapping(source = "enderecoResponse.cep", target = "cep")
    @Mapping(source = "enderecoResponse.uf", target = "estado")
    @Mapping(source = "enderecoResponse.localidade", target = "cidade")
    Pessoa pessoaRequestToPessoaEntity(PessoaRequest pessoaRequest, EnderecoResponse enderecoResponse);

    PessoaResponse pessoaEntityToPessoaResponse(Pessoa pessoa);
}
