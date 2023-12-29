package br.com.brunadelmouro.apiserasa.dto.request;

import br.com.brunadelmouro.apiserasa.annotations.NoHyphen;
import br.com.brunadelmouro.apiserasa.annotations.ScoreRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PessoaRequest(
    @NotBlank @Schema(example = "Bruna") String nome,
    @NotNull @Schema(example = "21") Integer idade,
    @NotBlank @Schema(example = "999999999") String telefone,
    @NotBlank @NoHyphen @Schema(example = "37500190")
        String cep,
    @NotNull @ScoreRange @Schema(example = "100") Integer score) {}
