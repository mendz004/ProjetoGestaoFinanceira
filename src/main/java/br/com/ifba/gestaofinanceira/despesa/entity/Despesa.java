package br.com.ifba.gestaofinanceira.despesa.entity;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "despesa")
@Data
@NoArgsConstructor
public class Despesa  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor", nullable = false)
    private Double valor;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "descricao", nullable = false)
    private String descricao; // Aqui o usuário pode simplesmente escrever "Almoço" ou "Mercado"

    @Column(name = "formaPagamento", nullable = false)
    private String formaPagamento;

    private Boolean efetivada;

    // A despesa pode ser paga saindo de uma Conta
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;
}
