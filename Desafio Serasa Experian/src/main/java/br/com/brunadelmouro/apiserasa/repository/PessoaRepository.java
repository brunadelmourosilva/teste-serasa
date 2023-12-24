package br.com.brunadelmouro.apiserasa.repository;

import br.com.brunadelmouro.apiserasa.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, String> {}
