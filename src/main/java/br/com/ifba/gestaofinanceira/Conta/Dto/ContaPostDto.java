package br.com.ifba.gestaofinanceira.Conta.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContaPostDto {

    @JsonProperty(value = "nomeConta")
    @NotNull(message = "nome da conta deve ser obrigatorio")
    private String nomeConta;

    @JsonProperty(value = "saldoAtual")
    @NotNull(message = "por favor, insira o valor inicial da sua conta")
    private Double saldoAtual;     // O saldo que a conta tem no momento da criação

    @JsonProperty(value = "tipo")
    private String tipo;         // Ex: CORRENTE, POUPANCA

    @JsonProperty("usuarioId")
    private Long usuarioId;
}
