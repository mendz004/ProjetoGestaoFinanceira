package br.com.ifba.gestaofinanceira.objetivo.dto;

import br.com.ifba.gestaofinanceira.objetivo.emum.StatusObjetivo;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ObjetivoGetDto {


    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "valorAlvo")
    private Double valorAlvo;

    @JsonProperty(value = "valorAtual")
    private Double valorAtual;

    @JsonProperty(value = "dataLimite")
    private LocalDate dataLimite;

    @JsonProperty(value = "status")
    private StatusObjetivo status;

}
