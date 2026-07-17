package br.com.ifba.gestaofinanceira.receita.service;

import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;

import java.util.List;

public interface ReceitaIService {

    Receita cadastrarReceita(ReceitaPostDto dto);

    List<Receita> findAll();

    void deleteById(Long id);

    Receita findById(Long id);

    Receita atualizar(Long id, Receita novaReceita);

    List<Receita> findByDescricao(String termo);

}
