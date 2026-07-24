package br.com.ifba.gestaofinanceira.receita.repository;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByContaIdAndDataBetween(Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    void deleteAllByConta(Conta conta);

    List<Receita> findByDescricaoContainingIgnoreCaseAndUsuarioId(String termo, Long usuarioId);

    List<Receita> findByUsuarioId(Long usuarioId);

    Optional<Receita> findByIdAndUsuarioId(Long id, Long usuarioId);
}
