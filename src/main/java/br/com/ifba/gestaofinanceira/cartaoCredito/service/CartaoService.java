package br.com.ifba.gestaofinanceira.cartaoCredito.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.repository.CartaoRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import br.com.ifba.gestaofinanceira.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoService implements CartaoIService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DespesaRepository despesaRepository;


    @Transactional
    @Override
    public Cartao cadastrarCartao(CartaoPostDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setLimiteTotal(dto.getLimiteTotal());
        cartao.setLimiteDisponivel(dto.getLimiteTotal());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaFechamento());
        cartao.setUsuario(usuario);

        return cartaoRepository.save(cartao);
    }

    @Override
    public List<Cartao> listarTodos() {
        return cartaoRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        //  Busca o cartão para garantir que ele existe
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cartão não encontrado com o ID: " + id));

        //  Apaga TODAS as despesas vinculadas a este cartão
        despesaRepository.deleteAllByCartao(cartao);

        cartaoRepository.delete(cartao);
    }

    @Transactional
    public Cartao atualizar(Long id, Cartao cartaoAtualizado) {
        Cartao cartaoExistente = cartaoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cartão não encontrado."));

        // Se o limite total mudou, reajusta o limite disponível proporcionalmente
        double diferencaLimite = cartaoAtualizado.getLimiteTotal() - cartaoExistente.getLimiteTotal();
        cartaoExistente.setLimiteDisponivel(cartaoExistente.getLimiteDisponivel() + diferencaLimite);

        // Atualiza os dados base
        cartaoExistente.setNome(cartaoAtualizado.getNome());
        cartaoExistente.setLimiteTotal(cartaoAtualizado.getLimiteTotal());
        cartaoExistente.setDiaFechamento(cartaoAtualizado.getDiaFechamento());
        cartaoExistente.setDiaVencimento(cartaoAtualizado.getDiaVencimento());

        return cartaoRepository.save(cartaoExistente);
    }

}
