package br.com.ifba.gestaofinanceira.despesa.entity;

import br.com.ifba.gestaofinanceira.cartaoCredito.entity.Cartao;
import br.com.ifba.gestaofinanceira.conta.entity.Conta;
import br.com.ifba.gestaofinanceira.orcamento.enun.CategoriaDespesa;
import br.com.ifba.gestaofinanceira.transacao.entity.Transacao;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "despesa")
@Data
@NoArgsConstructor
public class Despesa extends Transacao {


    @Column(name = "forma_pagamento", nullable = false)
    private String formaPagamento;

    private Boolean efetivada;

    @ManyToOne
    @JoinColumn(name = "cartaoId")
    private Cartao cartao;

    @ManyToOne
    @JoinColumn(name = "contaId")
    private Conta conta;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaDespesa categoria;

}
