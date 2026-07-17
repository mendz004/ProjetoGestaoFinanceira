package br.com.ifba.gestaofinanceira.despesa.repository;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByContaIdAndDataBetween(Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    void deleteAllByCartao(Cartao cartao);

    void deleteAllByConta(Conta conta);

    List<Despesa> findByDescricaoContainingIgnoreCase(String termo);
}
