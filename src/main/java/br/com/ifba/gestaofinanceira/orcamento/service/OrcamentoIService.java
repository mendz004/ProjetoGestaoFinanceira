package br.com.ifba.gestaofinanceira.orcamento.service;

import br.com.ifba.gestaofinanceira.orcamento.dto.OrcamentoPostDto;
import br.com.ifba.gestaofinanceira.orcamento.entity.Orcamento;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;

import java.util.List;
import java.util.Map;

public interface OrcamentoIService {

    Orcamento cadastrarOrcamento(OrcamentoPostDto dto, Long usuarioId);

    List<Orcamento> findAll(Long usuarioId);

    Map<CategoriaDespesa, Double> gerarSugestaoOrcamento(Long usuarioId);

    void atualizarGasto(Long usuarioId, Integer mes, Integer ano, CategoriaDespesa categoria, Double valorGasto);

    Orcamento atualizar(Long id, Orcamento orcamentoAtualizado, Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    Orcamento findByIdAndUsuario(Long id, Long usuarioId);

}
