package br.com.ifba.gestaofinanceira.conta.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContaGetDto {

    @JsonProperty(value = "nomeConta")
    private String nomeConta;

    @JsonProperty(value = "saldoAtual")
    private Double saldoAtual;     // O saldo que a conta tem no momento da criação

    @JsonProperty(value = "tipo")
    private String tipo;
}
