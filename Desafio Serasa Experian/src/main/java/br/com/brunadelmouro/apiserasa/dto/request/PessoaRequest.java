package br.com.brunadelmouro.apiserasa.dto.request;

public record PessoaRequest(
    String nome, Integer idade, String telefone, String cep, String score) {}
