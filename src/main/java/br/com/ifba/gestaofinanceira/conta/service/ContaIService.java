package br.com.ifba.gestaofinanceira.conta.service;

import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;

import java.util.List;

public interface ContaIService {

     Conta cadastrarConta(ContaPostDto dto);

    List<Conta> listarTodas();

}
