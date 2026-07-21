package br.com.ifba.gestaofinanceira.despesa.dto;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DespesaGetDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "valor")
    private Double valor;

    @JsonProperty(value = "data")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") // Formata o LocalDateTime para o JSON
    private LocalDateTime data;

    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "formaPagamento")
    private String formaPagamento;

    @JsonProperty(value = "efetivada")
    private Boolean efetivada;

    @JsonProperty("categoria")
    private CategoriaDespesa categoria;

    @JsonProperty("conta")
    private Conta conta;

    @JsonProperty("cartao")
    private Cartao cartao;

}