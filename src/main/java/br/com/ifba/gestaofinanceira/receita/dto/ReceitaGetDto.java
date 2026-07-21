package br.com.ifba.gestaofinanceira.receita.dto;

import br.com.ifba.gestaofinanceira.receita.enun.CategoriaReceita;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceitaGetDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "valor")
    private Double valor;

    @JsonProperty(value = "data")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;

    @JsonProperty(value = "descricao")
    private String descricao;

    @JsonProperty(value = "categoria")
    private CategoriaReceita origem;

    @JsonProperty(value = "conta")
    private String conta;
}
