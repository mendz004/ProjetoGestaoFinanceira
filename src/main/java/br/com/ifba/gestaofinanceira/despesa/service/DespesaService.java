package br.com.ifba.gestaofinanceira.despesa.service;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.cartaoCredito.repository.CartaoRepository;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.dto.DespesaPostDto;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.orcamento.service.OrcamentoIService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService implements DespesaIService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private OrcamentoIService orcamentoService;

    @Transactional
    @Override
    public Despesa registrarDespesa(DespesaPostDto dto) {

        Despesa despesa = new Despesa();

        despesa.setValor(dto.getValor());
        despesa.setData(dto.getData());
        despesa.setDescricao(dto.getDescricao());
        despesa.setCategoria(dto.getCategoria());
        despesa.setFormaPagamento(dto.getFormaPagamento());
        despesa.setEfetivada(dto.getEfetivada());

        Long usuarioId = null;

        if (dto.getContaId() != null) {

            Conta conta = contaRepository.findById(dto.getContaId())
                    .orElseThrow(() -> new BusinessException("Conta não encontrada"));

            despesa.setConta(conta);

            usuarioId = conta.getUsuario().getId();

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


            usuarioId = cartao.getUsuario().getId();

        } else {
            throw new BusinessException("Informe uma conta ou um cartão.");
        }

        // Avisa o Orçamento que o usuário gastou dinheiro
        Integer mesDespesa = despesa.getData().getMonthValue();
        Integer anoDespesa = despesa.getData().getYear();

        orcamentoService.atualizarGasto(usuarioId, mesDespesa, anoDespesa,
                despesa.getCategoria(), despesa.getValor());


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

    @Transactional
    public Despesa atualizar(Long id, Despesa novaDespesa) {
        Despesa despesaExistente = despesaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Despesa não encontrada."));

        //  REVERTE o impacto antigo (Devolve o dinheiro para a conta ou limite para o cartão)
        if (despesaExistente.getConta() != null && Boolean.TRUE.equals(despesaExistente.getEfetivada())) {
            Conta contaAntiga = despesaExistente.getConta();
            contaAntiga.setSaldoAtual(contaAntiga.getSaldoAtual() + despesaExistente.getValor());
            contaRepository.save(contaAntiga);

        } else if (despesaExistente.getCartao() != null) {
            Cartao cartaoAntigo = despesaExistente.getCartao();
            cartaoAntigo.setLimiteDisponivel(cartaoAntigo.getLimiteDisponivel() + despesaExistente.getValor());
            cartaoRepository.save(cartaoAntigo);
        }

        //  APLICA o novo impacto (Subtrai o novo valor da conta ou cartão selecionado)
        if (novaDespesa.getConta() != null) {
            Conta contaNova = contaRepository.findById(novaDespesa.getConta().getId()).orElseThrow();
            if (Boolean.TRUE.equals(novaDespesa.getEfetivada())) {
                contaNova.setSaldoAtual(contaNova.getSaldoAtual() - novaDespesa.getValor());
                contaRepository.save(contaNova);
            }
            despesaExistente.setConta(contaNova);
            despesaExistente.setCartao(null);

        } else if (novaDespesa.getCartao() != null) {
            Cartao cartaoNovo = cartaoRepository.findById(novaDespesa.getCartao().getId()).orElseThrow();
            cartaoNovo.setLimiteDisponivel(cartaoNovo.getLimiteDisponivel() - novaDespesa.getValor());
            cartaoRepository.save(cartaoNovo);
            despesaExistente.setCartao(cartaoNovo);
            despesaExistente.setConta(null);
        }

        //  Atualiza os dados da despesa
        despesaExistente.setDescricao(novaDespesa.getDescricao());
        despesaExistente.setValor(novaDespesa.getValor());
        despesaExistente.setData(novaDespesa.getData());
        despesaExistente.setCategoria(novaDespesa.getCategoria());
        despesaExistente.setConta(novaDespesa.getConta());
        return despesaRepository.save(despesaExistente);
    }

    @Override
    public List<Despesa> findByDescricao(String termo) {
        return despesaRepository.findByDescricaoContainingIgnoreCase(termo);
    }

}
