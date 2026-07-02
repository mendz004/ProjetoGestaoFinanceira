package br.com.ifba.gestaofinanceira.Despesa.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DespesaGetDto {

    @JsonProperty(value = "valor")
    private Double valor;

    @JsonProperty(value = "data")
    private LocalDate data;

    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "formaPagamento")
    private String formaPagamento;

}
