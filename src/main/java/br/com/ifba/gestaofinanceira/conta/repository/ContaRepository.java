package br.com.ifba.gestaofinanceira.conta.repository;

import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
}
