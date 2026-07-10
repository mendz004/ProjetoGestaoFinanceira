package br.com.ifba.gestaofinanceira.relatorio.repository;

import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioMensal, Long> {

}
