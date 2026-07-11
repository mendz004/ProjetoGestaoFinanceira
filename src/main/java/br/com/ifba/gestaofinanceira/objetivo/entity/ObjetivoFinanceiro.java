package br.com.ifba.gestaofinanceira.objetivo.entity;

import br.com.ifba.gestaofinanceira.Infraestructure.entity.PersistenceEntity;
import br.com.ifba.gestaofinanceira.objetivo.emum.StatusObjetivo;
import br.com.ifba.gestaofinanceira.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "objetivos_financeiros")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ObjetivoFinanceiro extends PersistenceEntity {

    @Column(name = "nome", nullable = false, length = 150)
    private String nome; // Ex: "Viagem para Europa"

    @Column(name = "valor_alvo", nullable = false)
    private Double valorAlvo; // Quanto precisa alcançar

    @Column(name = "valor_atual", nullable = false)
    private Double valorAtual = 0.0; // Começa sempre zerado

    @Column(name = "data_limite")
    private LocalDate dataLimite; // Prazo para atingir o objetivo

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StatusObjetivo status = StatusObjetivo.EM_ANDAMENTO;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
