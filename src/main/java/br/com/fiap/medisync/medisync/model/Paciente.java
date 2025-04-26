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
@Table(name = "paciente", schema = "medisync")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_paciente_usuario"))
    private Usuario usuario;

    @Column(name = "criadoEm", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ultimaAlteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = LocalDateTime.now();
}

