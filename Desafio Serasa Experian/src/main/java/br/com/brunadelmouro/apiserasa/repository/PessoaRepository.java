package br.com.brunadelmouro.apiserasa.repository;

import br.com.brunadelmouro.apiserasa.model.Pessoa;
import feign.Param;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {

  @Query(
      value =
          """
        SELECT * FROM pessoa
        WHERE (:nome IS NULL OR lower(NOME) LIKE lower(concat('%', :nome, '%')))
        AND (:idade IS NULL OR IDADE = :idade)
        AND (:cep IS NULL OR CEP = :cep)
          """,
      nativeQuery = true)
  List<Pessoa> findByFilters(
      @Param("nome") String nome, @Param("idade") Integer idade, @Param("cep") String cep);
}
