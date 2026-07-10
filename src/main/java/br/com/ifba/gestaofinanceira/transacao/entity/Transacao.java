package br.com.ifba.gestaofinanceira.transacao.entity;

import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "transacoes")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public abstract class Transacao extends PersistenceEntity {

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "data_transacao", nullable = false)
    private LocalDate data;

    @Column(name = "descricao", length = 255)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
}
