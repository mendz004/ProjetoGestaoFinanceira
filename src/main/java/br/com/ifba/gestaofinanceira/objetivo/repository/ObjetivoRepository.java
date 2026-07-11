package br.com.ifba.gestaofinanceira.objetivo.repository;

import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjetivoRepository extends JpaRepository<ObjetivoFinanceiro, Long> {

}
