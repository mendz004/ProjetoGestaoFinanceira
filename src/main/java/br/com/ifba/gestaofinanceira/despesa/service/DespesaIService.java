package br.com.ifba.gestaofinanceira.despesa.service;

import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;

import java.util.List;

public interface DespesaIService {

    Despesa registrarDespesa(DespesaPostDto despesaPostDto);

    List<Despesa> listarTodas();

    void excluirDespesa(Long id);

    Despesa buscarPorId(Long id);

}
