package br.com.ifba.gestaofinanceira.relatorio.repository;

import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioMensal, Long> {

    List<RelatorioMensal> findByUsuarioId(Long usuarioId);

}
