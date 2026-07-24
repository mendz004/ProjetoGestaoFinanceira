package br.com.ifba.gestaofinanceira.cartaoCredito.entity;

import br.com.ifba.gestaofinanceira.cartaoCredito.enun.StatusFatura;
import br.com.ifba.gestaofinanceira.despesa.entity.Despesa;
import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cartoes")
@Data
@NoArgsConstructor
public class Cartao extends PersistenceEntity {

    @Column(name = "nome", nullable = false, length = 20)
    private String nome; // Ex: Nubank, Itaú

    @Column(name = "limiteTotal", nullable = false)
    private Double limiteTotal;

    private Double limiteDisponivel;

    private StatusFatura statusFatura;


    @Column(name = "diaFechamento", nullable = false)
    private Date diaFechamento;

    @Column(name = "diaVencimento", nullable = false)
    private Date diaVencimento;

    // O cartão pertence a um usuário
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Um cartão pode ter várias despesas na fatura
    @JsonIgnoreProperties("cartao")
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Despesa> despesas;

}
