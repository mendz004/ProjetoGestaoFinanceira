package br.com.ifba.gestaofinanceira.cartaoCredito.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.dto.CartaoPostDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.dto.PagamentoFaturaDto;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.repository.CartaoRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
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

    @Autowired
    private ContaRepository contaRepository;


    @Transactional
    @Override
    public Cartao cadastrarCartao(CartaoPostDto dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setLimiteTotal(dto.getLimiteTotal());
        cartao.setLimiteDisponivel(dto.getLimiteTotal());
        cartao.setDiaFechamento(dto.getDiaFechamento());
        cartao.setDiaVencimento(dto.getDiaFechamento());
        cartao.setUsuario(usuario);

        cartao.setUsuario(usuario);

        return cartaoRepository.save(cartao);
    }

    @Override
    public List<Cartao> findAll(Long usuarioId) {

        return cartaoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public Cartao findByIdAndUsuario(Long id, Long usuarioId) {
        return cartaoRepository.findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new BusinessException("Cartao não encontrada ou acesso negado."));
    }

    @Transactional
    @Override
    public void deleteById(Long id, Long usuarioId) {

        Cartao cartao = findByIdAndUsuario(id, usuarioId);

        //  Apaga TODAS as despesas vinculadas a este cartão
        despesaRepository.deleteAllByCartao(cartao);

        cartaoRepository.delete(cartao);
    }

    @Transactional
    @Override
    public Cartao atualizar(Long id, Cartao cartaoAtualizado, Long usuarioId) {

        Cartao cartaoExistente = findByIdAndUsuario(id, usuarioId);

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

    @Transactional
    @Override
    public void pagarFatura(Long cartaoId, PagamentoFaturaDto dto) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new BusinessException("Cartão não encontrado."));

        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada."));

        if (conta.getSaldoAtual() < dto.getValor()) {
            throw new BusinessException("Saldo insuficiente na conta origem.");
        }

        // 1. Subtrai o dinheiro da Conta
        conta.setSaldoAtual(conta.getSaldoAtual() - dto.getValor());
        contaRepository.save(conta);

        // 2. Restaura o limite do Cartão
        cartao.setLimiteDisponivel(cartao.getLimiteDisponivel() + dto.getValor());
        cartaoRepository.save(cartao);

        // 3. Desvincula as despesas pagas para zerar a fatura atual no React
        List<Despesa> despesasDoCartao = despesaRepository.findByCartaoId(cartaoId);
        for (Despesa despesa : despesasDoCartao) {
            despesa.setCartao(null);
            despesaRepository.save(despesa);
        }
    }
}
