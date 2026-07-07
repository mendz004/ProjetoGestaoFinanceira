package br.com.ifba.gestaofinanceira.despesa.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.repository.CartaoRepository;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService implements DespesaIService{

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    @Override
    public Despesa registrarDespesa(DespesaPostDto dto) {

        Despesa despesa = new Despesa();

        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setDescricao(dto.getDescricao());
        despesa.setEfetivada(dto.getEfetivada());
        despesa.setFormaPagamento(dto.getFormaPagamento());

        if (dto.getContaId() != null) {

            Conta conta = contaRepository.findById(dto.getContaId())
                    .orElseThrow(() -> new BusinessException("Conta não encontrada"));

            despesa.setConta(conta);

            if (Boolean.TRUE.equals(dto.getEfetivada())) {
                conta.setSaldoAtual(conta.getSaldoAtual() - dto.getValor());
                contaRepository.save(conta);
            }

        } else if (dto.getCartaoId() != null) {

            Cartao cartao = cartaoRepository.findById(dto.getCartaoId())
                    .orElseThrow(() -> new BusinessException("Cartão não encontrado"));

            if (cartao.getLimiteDisponivel() < dto.getValor()) {
                throw new BusinessException("Limite insuficiente.");
            }

            cartao.setLimiteDisponivel(cartao.getLimiteDisponivel() - dto.getValor());
            cartaoRepository.save(cartao);

            despesa.setCartao(cartao);

        } else {
            throw new BusinessException("Informe uma conta ou um cartão.");
        }

        return despesaRepository.save(despesa);
    }

    @Override
    public List<Despesa> listarTodas() {
        return despesaRepository.findAll();
    }

    @Override
    public Despesa buscarPorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Despesa não encontrada."));
    }

    @Transactional
    @Override
    public void excluirDespesa(Long id) {
        Despesa despesa = buscarPorId(id);

        // Devolve o dinheiro para a conta ou limite do cartão antes de apagar
        if (despesa.getConta() != null && Boolean.TRUE.equals(despesa.getEfetivada())) {
            Conta conta = despesa.getConta();
            conta.setSaldoAtual(conta.getSaldoAtual() + despesa.getValor());
            contaRepository.save(conta);
        } else if (despesa.getCartao() != null) {
            Cartao cartao = despesa.getCartao();
            cartao.setLimiteDisponivel(cartao.getLimiteDisponivel() + despesa.getValor());
            cartaoRepository.save(cartao);
        }

        despesaRepository.delete(despesa);
    }

}
