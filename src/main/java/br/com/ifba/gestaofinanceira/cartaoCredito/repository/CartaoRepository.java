package br.com.ifba.gestaofinanceira.cartaoCredito.repository;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
