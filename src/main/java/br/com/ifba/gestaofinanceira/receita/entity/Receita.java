package br.com.ifba.gestaofinanceira.receita.entity;

import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.receita.enun.CategoriaReceita;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "receitas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Receita extends PersistenceEntity {


    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(length = 255)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaReceita origem;

    // A receita precisa saber em qual conta o dinheiro entrou
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
