package br.com.ifba.gestaofinanceira.orcamento.dto;

import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrcamentoPostDto {

    @JsonProperty(value = "mes")
    private Integer mes;

    @JsonProperty(value = "ano")
    private Integer ano;

    @JsonProperty(value = "valorAtual")
    private Double valorAtual;

    @JsonProperty(value = "valorLimite")
    @NotNull(message = "Por favor, insira um valor limite para gasto!")
    private Double valorLimite;

    @JsonProperty(value = "categoria")
    @NotNull(message = "Categoria deve ser obrigátorio!")
    private CategoriaDespesa categoria;

    @JsonProperty(value = "usuarioId")
    private Long usuarioId;

}
