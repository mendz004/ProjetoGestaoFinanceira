package br.com.ifba.gestaofinanceira.receita.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReceitaGetDto {

    @JsonProperty(value = "data")
    private LocalDate data;

    @JsonProperty(value = "descricao")
    private String descricao;


    @JsonProperty(value = "origem")
    private String origem;
}
