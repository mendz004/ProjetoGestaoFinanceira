package br.com.ifba.gestaofinanceira.objetivo.service;

import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoPostDto;
import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;

import java.util.List;

public interface ObjetivoIService {

    ObjetivoFinanceiro cadastrarObjetivo(ObjetivoPostDto dto, Long usuarioId);

    List<ObjetivoFinanceiro> findAll(Long usuarioId);

    ObjetivoFinanceiro depositar(Long id, Double valorDepositado, Long usuarioId);

    ObjetivoFinanceiro atualizar(Long id, ObjetivoFinanceiro objetivoAtualizado, Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    ObjetivoFinanceiro findByIdAndUsuario(Long id, Long usuarioId);

}
