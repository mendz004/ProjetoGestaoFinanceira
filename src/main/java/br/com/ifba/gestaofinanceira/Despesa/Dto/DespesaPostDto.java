package br.com.ifba.gestaofinanceira.Despesa.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DespesaPostDto {

    @JsonProperty(value = "valor")
    @NotNull(message = "por favor, insira o valor da despesa")
    private Double valor;

    @JsonProperty(value = "data")
    @NotNull(message = "data deve ser obrigatoria")
    private LocalDate data;

    @JsonProperty(value = "descricao")
    @NotNull(message = "Erro, por favor insira a causa da despesa")
    private String descricao;

    @JsonProperty(value = "efetivada")
    private Boolean efetivada;

    @JsonProperty(value = "formaPagamento")
    private String formaPagamento;

    @JsonProperty(value = "contaId")
    private Long contaId;

    @JsonProperty(value = "cartaoId")
    private Long cartaoId;

}
