package br.com.ifba.gestaofinanceira.cartaoCredito.dto;

import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CartaoGetDto {

    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "limiteTotal")
    private Double limiteTotal;

    private Date diaFechamento;

    private Date diaVencimento;

    private List<Despesa> despesas;
}
