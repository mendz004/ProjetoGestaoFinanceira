package br.com.ifba.gestaofinanceira.relatorio.service;

import br.com.ifba.gestaofinanceira.Infraestructure.exception.BusinessException;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.conta.repository.ContaRepository;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.despesa.repository.DespesaRepository;
import br.com.ifba.gestaofinanceira.receita.entity.Receita;
import br.com.ifba.gestaofinanceira.receita.repository.ReceitaRepository;
import br.com.ifba.gestaofinanceira.relatorio.dto.ExtratoItemDto;
import br.com.ifba.gestaofinanceira.relatorio.dto.RelatorioMensalDto;
import br.com.ifba.gestaofinanceira.relatorio.entity.RelatorioMensal;
import br.com.ifba.gestaofinanceira.relatorio.repository.RelatorioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RelatorioService implements RelatorioIService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private RelatorioRepository relatorioRepository;

    @Autowired
    private ContaRepository contaRepository; // 1. Injetamos o repositório de contas para validar a permissão

    @Transactional
    @Override
    public RelatorioMensalDto gerarRelatorioMensal(Long contaId, Integer mes, Integer ano, Long usuarioId) { // 2. Recebe o usuarioId

        // 3. SEGURANÇA: Valida se a conta informada pertence mesmo ao usuário logado
        Conta conta = contaRepository.findByIdAndUsuarioId(contaId, usuarioId)
                .orElseThrow(() -> new BusinessException("Conta não encontrada ou não pertence a este usuário."));

        // Define o primeiro e o último dia do mês para a busca no banco
        YearMonth anoMes = YearMonth.of(ano, mes);
        LocalDateTime dataInicio = anoMes.atDay(1).atStartOfDay(); // Ex: 01/07/2026 00:00:00
        LocalDateTime dataFim = anoMes.atEndOfMonth().atTime(23, 59, 59); // Ex: 31/07/2026 23:59:59

        // Busca Despesas e Receitas daquela conta naquele período
        List<Despesa> despesas = despesaRepository.findByContaIdAndDataBetween(contaId, dataInicio, dataFim);
        List<Receita> receitas = receitaRepository.findByContaIdAndDataBetween(contaId, dataInicio, dataFim);

        List<ExtratoItemDto> itensExtrato = new ArrayList<>();
        double totalDespesas = 0.0;
        double totalReceitas = 0.0;

        for (Despesa d : despesas) {
            itensExtrato.add(new ExtratoItemDto(
                    d.getData(), d.getValor(), d.getDescricao(), d.getCategoria().name(), "DESPESA"
            ));
            totalDespesas += d.getValor();
        }

        for (Receita r : receitas) {
            itensExtrato.add(new ExtratoItemDto(
                    r.getData(), r.getValor(), r.getDescricao(), r.getOrigem().name(), "RECEITA"
            ));
            totalReceitas += r.getValor();
        }

        itensExtrato.sort(Comparator.comparing(ExtratoItemDto::getData).reversed());

        // Monta o relatório final e devolve
        RelatorioMensalDto relatorio = new RelatorioMensalDto();
        relatorio.setTotalDespesas(totalDespesas);
        relatorio.setTotalReceitas(totalReceitas);
        relatorio.setSaldoPeriodo(totalReceitas - totalDespesas);
        relatorio.setTransacoes(itensExtrato);

        return relatorio;
    }

    @Override
    public List<RelatorioMensal> findAllByUsuarioId(Long usuarioId) { // 4. Alterado para buscar apenas do usuário logado
        return relatorioRepository.findByUsuarioId(usuarioId);
    }
}