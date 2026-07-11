package br.com.ifba.gestaofinanceira.alerta.service;

import br.com.ifba.gestaofinanceira.alerta.dto.AlertaGetDto;
import br.com.ifba.gestaofinanceira.alerta.enun.NivelAlerta;
import br.com.ifba.gestaofinanceira.alerta.enun.TipoAlerta;
import br.com.ifba.gestaofinanceira.orcamento.entity.Orcamento;
import br.com.ifba.gestaofinanceira.orcamento.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlertaService implements AlertaIService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

    public List<AlertaGetDto> buscarAlertasDoUsuario(Long usuarioId, Integer mes, Integer ano) {
        List<AlertaGetDto> alertas = new ArrayList<>();

        List<Orcamento> orcamentos = orcamentoRepository.findByUsuarioIdAndMesAndAno(usuarioId, mes, ano);

        for (Orcamento orcamento : orcamentos) {
            double percentual = (orcamento.getValorGasto() / orcamento.getValorLimite()) * 100;

            if (percentual >= 100) {
                alertas.add(new AlertaGetDto(
                        TipoAlerta.ORCAMENTO,
                        NivelAlerta.CRITICO,
                        "Você estourou o limite de " + orcamento.getCategoria() + "!"
                ));
            } else if (percentual >= 80) {
                alertas.add(new AlertaGetDto(
                        TipoAlerta.ORCAMENTO,
                        NivelAlerta.ATENCAO,
                        "Você já consumiu " + String.format("%.1f", percentual) + "% do limite de " + orcamento.getCategoria() + "."
                ));
            }
        }

        return alertas;
    }

}