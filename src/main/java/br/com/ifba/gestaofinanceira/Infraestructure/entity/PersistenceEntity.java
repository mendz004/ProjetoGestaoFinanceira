package br.com.ifba.gestaofinanceira.Infraestructure.entity;


import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public class PersistenceEntity {

    @Id //primey key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
