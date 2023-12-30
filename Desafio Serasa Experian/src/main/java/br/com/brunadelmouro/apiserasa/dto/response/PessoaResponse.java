package br.com.brunadelmouro.apiserasa.dto.response;


public record PessoaResponse(
    String id,
    String nome,
    Integer idade,
    String telefone,
    Integer score,
    String scoreDescricao,
    String cep,
    String estado,
    String cidade,
    String bairro,
    String logradouro) {}
