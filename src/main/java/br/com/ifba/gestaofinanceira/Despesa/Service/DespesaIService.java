package br.com.ifba.gestaofinanceira.Despesa.Service;

import br.com.ifba.gestaofinanceira.Despesa.Dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.Despesa.Entity.Despesa;

import java.util.List;

public interface DespesaIService {

    Despesa registrarDespesa(DespesaPostDto despesaPostDto);

    List<Despesa> listarTodas();

    void excluirDespesa(Long id);

    Despesa buscarPorId(Long id);

}
