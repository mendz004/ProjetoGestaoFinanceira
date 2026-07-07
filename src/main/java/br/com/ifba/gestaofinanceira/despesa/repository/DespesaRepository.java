package br.com.ifba.gestaofinanceira.despesa.repository;

import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
