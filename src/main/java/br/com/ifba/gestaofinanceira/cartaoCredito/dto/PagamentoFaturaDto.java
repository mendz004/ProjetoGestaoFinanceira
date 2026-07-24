package br.com.ifba.gestaofinanceira.cartaoCredito.dto;

import lombok.Data;

@Data
public class PagamentoFaturaDto {

    private Long contaId;
    private Double valor;

}
