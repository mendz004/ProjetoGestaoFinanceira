package br.com.ifba.gestaofinanceira.conta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContaGetDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "nomeConta")
    private String nomeConta;

    @JsonProperty(value = "saldoAtual")
    private Double saldoAtual;
    @JsonProperty(value = "tipo")
    private String tipo;
}
