package br.com.ifba.gestaofinanceira.Conta.Entity;

import br.com.ifba.gestaofinanceira.Infraestructure.Entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.Usuario.Entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conta")
@Data
@NoArgsConstructor
public class Conta extends PersistenceEntity {

    @Column(name = "nomeConta", nullable = false, length = 100)
    private String nomeConta;

    @Column(name = "saldoAtual", nullable = false)
    private Double saldoAtual;

    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
