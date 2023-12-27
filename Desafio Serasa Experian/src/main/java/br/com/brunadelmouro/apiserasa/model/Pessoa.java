package br.com.brunadelmouro.apiserasa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PESSOA")
public class Pessoa {

  @Column(name = "PESSOA_ID")
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  private String id;

  @Column(name = "NOME")
  private String nome;

  @Column(name = "IDADE")
  private Integer idade;

  @Column(name = "TELEFONE")
  private String telefone;

  @Column(name = "SCORE")
  private Integer score;

  @Column(name = "SCORE_DESCRICAO")
  private String scoreDescricao;

  @Column(name = "CEP")
  private String cep;

  @Column(name = "ESTADO")
  private String estado;

  @Column(name = "CIDADE")
  private String cidade;

  @Column(name = "BAIRRO")
  private String bairro;

  @Column(name = "LOGRADOURO")
  private String logradouro;
}
