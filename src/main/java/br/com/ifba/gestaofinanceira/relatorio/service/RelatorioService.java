package br.com.ifba.gestaofinanceira.relatorio.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioPostDto;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;
import br.com.ifba.gestaofinanceira.relatorio.repository.RelatorioRepository;
import br.com.ifba.gestaofinanceira.transacao.entity.Transacao;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService implements RelatorioIService {

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Transactional
    @Override
    public RelatorioMensal gerarRelatorio(RelatorioPostDto dto) {
        //  Busca a conta (que já vem com as transações embutidas)
        Conta conta = contaRepository.findById(dto.getContaId())
                .orElseThrow(() -> new BusinessException("Conta não encontrada."));

        double somaReceitas = 0.0;
        double somaDespesas = 0.0;

        // Analisa os dados de transação
        if (conta.getTransacoes() != null) {
            for (Transacao t : conta.getTransacoes()) {

                // Primeiro verifica se a data NÃO é nula!
                if (t.getData() != null && t.getData().getMonthValue() == dto.getMes() && t.getData().getYear() == dto.getAno()) {

                    // Separa as instâncias usando herança e faz as somas
                    if (t instanceof Receita) {
                        somaReceitas += t.getValor();
                    } else if (t instanceof Despesa) {
                        somaDespesas += t.getValor();
                    }
                }
            }
        }

        //  Monta o relatório e salva
        RelatorioMensal relatorio = new RelatorioMensal();
        relatorio.setMes(dto.getMes());
        relatorio.setAno(dto.getAno());
        relatorio.setConta(conta);
        relatorio.setTotalReceitas(somaReceitas);
        relatorio.setTotalDespesas(somaDespesas);
        relatorio.setSaldoMensal(somaReceitas - somaDespesas);

        return relatorioRepository.save(relatorio);
    }

    @Override
    public List<RelatorioMensal> findAll() {
        return relatorioRepository.findAll();
    }
}



