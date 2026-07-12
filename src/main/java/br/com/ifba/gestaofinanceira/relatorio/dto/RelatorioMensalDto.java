package br.com.ifba.gestaofinanceira.relatorio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RelatorioMensalDto {

    @JsonProperty(value = "totalReceitas")
    private Double totalReceitas;

    @JsonProperty(value = "totalDespesas")
    private Double totalDespesas;

    @JsonProperty(value = "saldoPeriodo")
    private Double saldoPeriodo;

    @JsonProperty(value = "transacoes")
    private List<ExtratoItemDto> transacoes;

}
