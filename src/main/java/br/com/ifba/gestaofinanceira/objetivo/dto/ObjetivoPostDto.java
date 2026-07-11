package br.com.ifba.gestaofinanceira.objetivo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ObjetivoPostDto {

    @JsonProperty(value = "nome")
    @NotNull(message = "Por favor, insira um nome para o seu objetivo!")
    private String nome;

    @JsonProperty(value = "valorAlvo")
    @NotNull(message = "Por favor. insira um valor alvo para o seu objetivo!")
    private Double valorAlvo;

    @JsonProperty(value = "dataLimite")
    @NotNull(message = "Defina uma data limite para conseguir o seu objetivo!")
    private LocalDate dataLimite;

    @JsonProperty("usuarioId")
    private Long usuarioId;
}
