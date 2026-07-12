package br.com.ifba.gestaofinanceira.relatorio.service;

import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioMensalDto;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;

import java.util.List;

public interface RelatorioIService {

    RelatorioMensalDto gerarRelatorioMensal(Long contaId, Integer mes, Integer ano);
    List<RelatorioMensal> findAll();

}
