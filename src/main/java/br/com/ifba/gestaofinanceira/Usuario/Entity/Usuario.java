package br.com.ifba.gestaofinanceira.Usuario.Entity;

import br.com.ifba.gestaofinanceira.Conta.Entity.Conta;
import br.com.ifba.gestaofinanceira.Infraestructure.Entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
public class Usuario extends PersistenceEntity {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    // Um usuário tem várias contas (Composição/Agregação)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Conta> contas;

}
