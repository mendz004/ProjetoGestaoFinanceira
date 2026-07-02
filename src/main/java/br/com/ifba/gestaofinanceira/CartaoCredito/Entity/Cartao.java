package br.com.ifba.gestaofinanceira.CartaoCredito.Entity;

import br.com.ifba.gestaofinanceira.Despesa.Entity.Despesa;
import br.com.ifba.gestaofinanceira.Infraestructure.Entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;
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

    @Column(name = "diaFechamento", nullable = false)
    private Date diaFechamento;

    @Column(name = "diaVencimento", nullable = false)
    private Date diaVencimento;

    // O cartão pertence a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Um cartão pode ter várias despesas na fatura
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Despesa> despesas;

}
