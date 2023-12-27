package br.com.brunadelmouro.apiserasa.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ScoreDescricaoEnum {
  INSUFICIENTE("Insuficiente"),
  INACEITAVEL("Inaceitável"),
  ACEITAVEL("Aceitável"),
  RECOMENDAVEL("Recomendável");

  private final String scoreDescricao;

  public String getScoreDescricao() {
    return this.scoreDescricao;
  }
}
