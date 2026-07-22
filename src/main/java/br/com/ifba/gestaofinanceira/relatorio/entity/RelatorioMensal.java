package br.com.ifba.gestaofinanceira.relatorio.entity;

import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "relatorios")
@Data
@NoArgsConstructor
public class RelatorioMensal extends PersistenceEntity {

    @Column(name = "mes", nullable = false)
    private Integer mes; // Ex: 7 para Julho

    @Column(name = "ano", nullable = false)
    private Integer ano; // Ex: 2026

    @Column(name = "total_receitas", nullable = false)
    private Double totalReceitas;

    @Column(name = "total_despesas", nullable = false)
    private Double totalDespesas;

    @Column(name = "saldo_mensal", nullable = false)
    private Double saldoMensal;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
