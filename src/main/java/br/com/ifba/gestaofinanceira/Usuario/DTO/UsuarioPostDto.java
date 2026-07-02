package br.com.ifba.gestaofinanceira.Usuario.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioPostDto {



    @JsonProperty(value = "nome")
    @NotNull(message = "Nome de usuário deve ser obrigatório!")
    private String nome;

    @JsonProperty(value = "email")
    @NotNull(message = "Email deve ser obrigatório!")
    @Email(message = "Insira um E-mail válido!")
    private String email;

    @JsonProperty(value = "senha")
    @NotNull(message = "Senha deve ser obrigatório!")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres!")
    private String senha;


}
