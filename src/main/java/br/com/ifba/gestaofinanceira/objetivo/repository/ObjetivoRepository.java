package br.com.ifba.gestaofinanceira.objetivo.repository;

import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ObjetivoRepository extends JpaRepository<ObjetivoFinanceiro, Long> {

    List<ObjetivoFinanceiro> findByUsuarioId(Long usuarioId);

    Optional<ObjetivoFinanceiro> findByIdAndUsuarioId(Long id, Long usuarioId);
}
