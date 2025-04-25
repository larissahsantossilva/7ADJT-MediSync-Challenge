package br.com.fiap.medisync.medisync.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "especialidade", schema = "medisync")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "criado_em", nullable = false)
    private LocalDate criado_em = LocalDate.now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDate ultima_alteracao = LocalDate.now();

}
