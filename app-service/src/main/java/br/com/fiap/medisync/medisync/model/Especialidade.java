package br.com.fiap.medisync.medisync.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name = "criadoEm", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ultimaAlteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = LocalDateTime.now();

}
