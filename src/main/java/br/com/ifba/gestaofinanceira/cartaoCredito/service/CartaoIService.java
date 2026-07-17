package br.com.ifba.gestaofinanceira.cartaoCredito.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;

import java.util.List;

public interface CartaoIService {

    Cartao cadastrarCartao(CartaoPostDto dto);

    List<Cartao> listarTodos();

    void deleteById(Long id);
}
