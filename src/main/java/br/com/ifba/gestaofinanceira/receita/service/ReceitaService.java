package br.com.ifba.gestaofinanceira.receita.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.receita.dto.ReceitaPostDto;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceitaService implements ReceitaIService{

    @Autowired
    private  ReceitaRepository receitaRepository;

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


}
