package br.com.ifba.gestaofinanceira.receita.repository;

import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    List<Receita> findByContaIdAndDataBetween(Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim);
}
