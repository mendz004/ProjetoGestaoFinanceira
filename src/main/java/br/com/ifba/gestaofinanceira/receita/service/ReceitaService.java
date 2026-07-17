package br.com.ifba.gestaofinanceira.receita.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService implements ReceitaIService {

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    @Override
    public Receita cadastrarReceita(ReceitaPostDto dto) {
        //  Busca a conta no banco de dados
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada."));

        Double saldoAtual = conta.getSaldoAtual() != null ? conta.getSaldoAtual() : 0.0;
        conta.setSaldoAtual(saldoAtual + dto.getValor());
        contaRepository.save(conta); // Salva a conta com o novo saldo

        Receita receita = new Receita();
        receita.setValor(dto.getValor());
        receita.setData(dto.getData());
        receita.setDescricao(dto.getDescricao());
        receita.setOrigem(dto.getOrigem());
        receita.setConta(conta);

        return receitaRepository.save(receita);
    }

    @Override
    public List<Receita> findAll() {
        return receitaRepository.findAll();
    }

    @Override
    public Receita findById(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Receita não encontrada."));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Receita receita = findById(id);

        //  Subtrai o valor da conta antes de apagar a receita
        if (receita.getConta() != null) {
            Conta conta = receita.getConta();
            conta.setSaldoAtual(conta.getSaldoAtual() - receita.getValor());
            contaRepository.save(conta);
        }

        // Deleta a receita do banco de dados
        receitaRepository.delete(receita);
    }

    @Transactional
    @Override
    public Receita atualizar(Long id, Receita novaReceita) {
        Receita receitaExistente = receitaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Receita não encontrada."));

        //  REVERTE o impacto antigo (Tira o dinheiro da conta antiga)
        if (receitaExistente.getConta() != null) {
            Conta contaAntiga = receitaExistente.getConta();
            contaAntiga.setSaldoAtual(contaAntiga.getSaldoAtual() - receitaExistente.getValor());
            contaRepository.save(contaAntiga);
        }

        //  APLICA o novo impacto (Coloca o novo dinheiro na nova conta)
        if (novaReceita.getConta() != null) {
            // Busca a conta atualizada para garantir que temos a versão mais recente do banco
            Conta contaNova = contaRepository.findById(novaReceita.getConta().getId())
                    .orElseThrow(() -> new BusinessException("Conta de destino não encontrada."));

            contaNova.setSaldoAtual(contaNova.getSaldoAtual() + novaReceita.getValor());
            receitaExistente.setConta(contaNova);
            contaRepository.save(contaNova);
        }

        //  Atualiza os dados da receita
        receitaExistente.setDescricao(novaReceita.getDescricao());
        receitaExistente.setValor(novaReceita.getValor());
        receitaExistente.setData(novaReceita.getData());
        receitaExistente.setOrigem(novaReceita.getOrigem());

        return receitaRepository.save(receitaExistente);
    }

    @Override
    public List<Receita> findByDescricao(String termo) {
        return receitaRepository.findByDescricaoContainingIgnoreCase(termo);
    }

}
