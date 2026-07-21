package br.com.ifba.gestaofinanceira.despesa.dto;

import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DespesaPostDto {

    @JsonProperty(value = "valor")
    @NotNull(message = "por favor, insira o valor da despesa")
    private Double valor;

    @JsonProperty(value = "data")
    @NotNull(message = "data deve ser obrigatoria")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;

    @JsonProperty(value = "descricao")
    @NotNull(message = "Erro, por favor insira a causa da despesa")
    private String descricao;

    @JsonProperty(value = "efetivada")
    private Boolean efetivada;

    @JsonProperty(value = "formaPagamento")
    @NotNull(message = "Forma de Pagamento Obrigatória!")
    private String formaPagamento;

    @JsonProperty(value = "contaId")
    private Long contaId;

    @JsonProperty(value = "cartaoId")
    private Long cartaoId;

    @JsonProperty("categoria")
    private CategoriaDespesa categoria;

}
