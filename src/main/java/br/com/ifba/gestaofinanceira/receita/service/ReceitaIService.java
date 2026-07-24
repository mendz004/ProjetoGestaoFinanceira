package br.com.ifba.gestaofinanceira.receita.service;

import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;

import java.util.List;

public interface ReceitaIService {

    Receita cadastrarReceita(ReceitaPostDto dto, Long usuarioId);

    List<Receita> findAllByUsuario(Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    Receita findByIdAndUsuario(Long id, Long usuarioId);

    Receita atualizar(Long id, Receita novaReceita, Long usuarioId);

    List<Receita> findByDescricao(String termo, Long usuarioId);

}
