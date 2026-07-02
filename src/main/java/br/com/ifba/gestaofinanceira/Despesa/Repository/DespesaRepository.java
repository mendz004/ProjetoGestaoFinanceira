package br.com.ifba.gestaofinanceira.Despesa.Repository;

import br.com.ifba.gestaofinanceira.Despesa.Entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
