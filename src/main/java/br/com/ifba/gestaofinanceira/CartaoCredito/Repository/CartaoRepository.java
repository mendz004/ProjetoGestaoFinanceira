package br.com.ifba.gestaofinanceira.CartaoCredito.Repository;

import br.com.ifba.gestaofinanceira.CartaoCredito.Entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}
