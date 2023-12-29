package br.com.brunadelmouro.apiserasa.dto.request;

import br.com.brunadelmouro.apiserasa.annotations.NoHyphen;
import br.com.brunadelmouro.apiserasa.annotations.ScoreRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaRequest(
    @NotBlank String nome,
    @NotNull Integer idade,
    @NotBlank String telefone,
    @NotBlank @NoHyphen String cep,
    @NotNull @ScoreRange Integer score) {}
