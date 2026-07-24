package br.com.ifba.gestaofinanceira.conta.service;

import br.com.ifba.gestaofinanceira.conta.dto.ContaPostDto;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;

import java.util.List;

public interface ContaIService {

    Conta cadastrarConta(ContaPostDto dto, Long usuarioId);

    List<Conta> findAllByUsuario(Long usuarioId);

    Conta findByIdAndUsuario(Long id, Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    Conta atualizar(Long id, Conta contaAtualizada, Long usuarioId);
}
