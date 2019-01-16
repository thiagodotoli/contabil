package br.com.comex.v1.thiago.contabil.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.comex.v1.thiago.contabil.model.Lancamento;

@Repository
public interface ContabilRepository extends JpaRepository<Lancamento, String> {
	Collection<Lancamento> findByContaContabil(Long contaContabil);
}
