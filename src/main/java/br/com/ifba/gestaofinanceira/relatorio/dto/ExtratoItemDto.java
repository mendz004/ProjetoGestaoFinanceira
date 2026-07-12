package br.com.ifba.gestaofinanceira.relatorio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoItemDto {

    private LocalDateTime data;
    private Double valor;
    private String descricao;
    private String categoria;
    private String tipo;

}
