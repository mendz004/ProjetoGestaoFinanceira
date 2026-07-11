package br.com.ifba.gestaofinanceira.objetivo.service;

import br.com.ifba.gestaofinanceira.objetivo.dto.ObjetivoPostDto;
import br.com.ifba.gestaofinanceira.objetivo.entity.ObjetivoFinanceiro;

import java.util.List;

public interface ObjetivoIService {

    ObjetivoFinanceiro cadastrarObjetivo(ObjetivoPostDto dto);
    List<ObjetivoFinanceiro> findAll();
    ObjetivoFinanceiro depositar(Long id, Double valorDepositado);
}
