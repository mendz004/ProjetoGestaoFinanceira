package br.com.ifba.gestaofinanceira.cartaoCredito.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.dto.PagamentoFaturaDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;

import java.util.List;

public interface CartaoIService {

    Cartao cadastrarCartao(CartaoPostDto dto, Long usuarioId);

    List<Cartao> findAll(Long usuarioId);

    Cartao findByIdAndUsuario(Long id, Long usuarioId);

    void deleteById(Long id, Long usuarioId);

    Cartao atualizar(Long id, Cartao cartaoAtualizado, Long usuarioId);

    void pagarFatura(Long cartaoId, PagamentoFaturaDto dto);
}
