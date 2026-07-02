package br.com.ifba.gestaofinanceira.CartaoCredito.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CartaoGetDto {

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "limiteTotal")
    private Double limiteTotal;

}
