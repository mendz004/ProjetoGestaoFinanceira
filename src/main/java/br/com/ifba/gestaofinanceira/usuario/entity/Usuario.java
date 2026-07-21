package br.com.ifba.gestaofinanceira.usuario.entity;

import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "usuarios")
@ToString(exclude = "contas")
@Data
@NoArgsConstructor
public class Usuario extends PersistenceEntity  {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "renda_mensal")
    private Double rendaMensal = 0.0;

    // Um usuário tem várias contas (Composição/Agregação)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Conta> contas;


}
