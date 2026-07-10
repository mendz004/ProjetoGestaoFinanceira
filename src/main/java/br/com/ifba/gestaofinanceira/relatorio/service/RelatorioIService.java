package br.com.ifba.gestaofinanceira.relatorio.service;

import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioPostDto;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;

import java.util.List;

public interface RelatorioIService {

    RelatorioMensal gerarRelatorio(RelatorioPostDto dto);
    List<RelatorioMensal> findAll();

}
