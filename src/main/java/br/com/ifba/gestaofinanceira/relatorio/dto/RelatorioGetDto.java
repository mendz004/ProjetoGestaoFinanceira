package br.com.ifba.gestaofinanceira.relatorio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RelatorioGetDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "mes")
    private Integer mes;

    @JsonProperty(value = "ano")
    private Integer ano;

    @JsonProperty(value = "totalreceitas")
    private Double totalReceitas;

    @JsonProperty(value = "totalDespesas")
    private Double totalDespesas;

    @JsonProperty(value = "saldoMensal")
    private Double saldoMensal;
}
