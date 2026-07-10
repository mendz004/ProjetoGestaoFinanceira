package br.com.ifba.gestaofinanceira.receita.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReceitaPostDto {

    @JsonProperty(value = "valor")
    @NotNull(message = "Valor deve ser obrigatório!")
    private Double valor;

    @JsonProperty(value = "data")
    @NotNull(message = "Data deve ser obrigatório!")
    private LocalDate data;

    @JsonProperty(value = "descricao")
    @NotNull(message = "Descrição deve ser obrigatório!")
    private String descricao;

    @JsonProperty(value = "contaId")
    private Long contaId;

    @JsonProperty(value = "origem")
    @NotNull(message = "Origem da receita deve ser obrigatório!")
    private String origem;
}
