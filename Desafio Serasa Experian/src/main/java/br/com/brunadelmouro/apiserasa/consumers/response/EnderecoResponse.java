package br.com.brunadelmouro.apiserasa.consumers.response;

public record EnderecoResponse(
    String cep, String uf, String localidade, String bairro, String logradouro) {}
