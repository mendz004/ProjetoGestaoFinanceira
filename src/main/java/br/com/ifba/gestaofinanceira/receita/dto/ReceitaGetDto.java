package br.com.ifba.gestaofinanceira.receita.dto;

import br.com.ifba.gestaofinanceira.receita.enun.CategoriaReceita;
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
    private CategoriaReceita origem;
}
