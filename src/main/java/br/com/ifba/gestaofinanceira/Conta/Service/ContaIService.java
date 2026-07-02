package br.com.ifba.gestaofinanceira.Conta.Service;

import br.com.ifba.gestaofinanceira.Conta.Dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.Conta.Entity.Conta;

import java.util.List;

public interface ContaIService {

     Conta cadastrarConta(ContaPostDto dto);

    List<Conta> listarTodas();

}
