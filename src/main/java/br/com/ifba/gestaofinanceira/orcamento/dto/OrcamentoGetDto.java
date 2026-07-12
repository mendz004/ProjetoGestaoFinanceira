package br.com.ifba.gestaofinanceira.orcamento.dto;

import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrcamentoGetDto {

    @JsonProperty(value = "mes")
    private Integer mes;

    @JsonProperty(value = "ano")
    private Integer ano;

    @JsonProperty(value = "valorLimite")
    private Double valorLimite;

    @JsonProperty(value = "valorAtual")
    private Double valorAtual;

    @JsonProperty(value = "categoria")
    private CategoriaDespesa categoria;


}
