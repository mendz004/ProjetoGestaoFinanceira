package br.com.ifba.gestaofinanceira.orcamento.entity;

import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orcamentos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Orcamento extends PersistenceEntity {

    @Column(name = "mes", nullable = false)
    private Integer mes;

    @Column(name = "ano", nullable = false)
    private Integer ano;

    @Column(name = "valor_limite", nullable = false)
    private Double valorLimite; // O teto que o usuário escolheu

    @Column(name = "valor_gasto", nullable = false)
    private Double valorGasto = 0.0; // O sistema que vai controlar isso!

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaDespesa categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}