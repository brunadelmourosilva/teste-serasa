package br.com.brunadelmouro.apiserasa.dto.response;


public record PessoaResponse(
    String id,
    String nome,
    String idade,
    String telefone,
    Integer score,
    Integer scoreDescricao,
    String cep,
    String estado,
    String cidade,
    String bairro,
    String logradouro) {}
