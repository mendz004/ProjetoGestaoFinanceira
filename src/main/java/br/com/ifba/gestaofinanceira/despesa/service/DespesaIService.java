package br.com.ifba.gestaofinanceira.despesa.service;

import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;

import java.util.List;

public interface DespesaIService {

    Despesa registrarDespesa(DespesaPostDto despesaPostDto, Long usuarioId);

    List<Despesa> findAll(Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    List<Despesa> findByDescricao(String termo, Long usuarioId);

    Despesa findByIdAndUsuario(Long id, Long usuarioId);
}
