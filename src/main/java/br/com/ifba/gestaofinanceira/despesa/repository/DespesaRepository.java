package br.com.ifba.gestaofinanceira.despesa.repository;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByContaIdAndDataBetween(Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim);

    void deleteAllByCartao(Cartao cartao);

    void deleteAllByConta(Conta conta);

    List<Despesa> findByDescricaoContainingIgnoreCaseAndUsuarioId(String termo, Long usuarioId);

    List<Despesa> findByUsuarioId(Long usuarioId);

    Long usuario(Usuario usuario);

    Optional<Despesa> findByIdAndUsuarioId(Long id, Long usuarioId);

    List<Despesa> findByCartaoId(Long cartaoId);

}
