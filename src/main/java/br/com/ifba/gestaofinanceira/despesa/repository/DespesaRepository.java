package br.com.ifba.gestaofinanceira.despesa.repository;

import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByContaIdAndDataBetween(Long contaId, LocalDateTime dataInicio, LocalDateTime dataFim);
}
