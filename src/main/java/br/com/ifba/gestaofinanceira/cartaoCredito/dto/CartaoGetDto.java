package br.com.ifba.gestaofinanceira.cartaoCredito.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CartaoGetDto {

    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "limiteTotal")
    private Double limiteTotal;

    private Date diaFechamento;

    private Date diaVencimento;
}
