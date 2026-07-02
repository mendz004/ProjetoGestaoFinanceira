package br.com.ifba.gestaofinanceira.CartaoCredito.Service;

import br.com.ifba.gestaofinanceira.CartaoCredito.Dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.CartaoCredito.Entity.Cartao;

import java.util.List;

public interface CartaoIService {

    Cartao cadastrarCartao(CartaoPostDto dto);

    List<Cartao> listarTodos();

}
