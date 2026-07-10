package br.com.ifba.gestaofinanceira.relatorio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelatorioPostDto {

    @JsonProperty(value = "mes")
    @NotNull(message = "Por favor, informar o mês!")
    private Integer mes;

    @JsonProperty(value = "ano")
    @NotNull(message = "Por favor, informe o ano!")
    private Integer ano;

    @JsonProperty(value = "contaId")
    private Long contaId;
}
