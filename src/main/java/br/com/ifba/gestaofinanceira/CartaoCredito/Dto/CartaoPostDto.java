package br.com.ifba.gestaofinanceira.CartaoCredito.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CartaoPostDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "limiteTotal")
    private Double limiteTotal;

    @JsonProperty(value = "diaFechamento")
    private Date diaFechamento;

    @JsonProperty(value = "diaVencimento")
    private Date diaVencimento;

    @JsonProperty(value = "usuarioId")
    private Long usuarioId;
}
