package br.com.ifba.gestaofinanceira.receita.entity;

import br.com.ifba.gestaofinanceira.transacao.entity.Transacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receitas")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Receita extends Transacao {

    @Column(name = "origem", nullable = false, length = 150)
    private String origem;

}
