package br.com.ifba.gestaofinanceira.CartaoCredito.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CartaoPostDto {

    @JsonProperty(value = "nome")
    @NotNull(message = "nome deve ser obrigatorio")
    private String nome;

    @JsonProperty(value = "limiteTotal")
    @NotNull(message = "por favor, insira o limite do cartão")
    private Double limiteTotal;

    @JsonProperty(value = "diaFechamento")
    @NotNull(message = "por favor, insira o data de fechamento da fatura")
    private Date diaFechamento;

    @JsonProperty(value = "diaVencimento")
    @NotNull(message = "por favor, insira a data de vencimeno da fatura")
    private Date diaVencimento;

    @JsonProperty(value = "usuarioId")
    private Long usuarioId;
}
