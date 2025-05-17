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
@ToString
@Table(name = "medico", schema = "medisync")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_medico_usuario"))
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEspecialidade", foreignKey = @ForeignKey(name = "fk_medico_especialidade"))
    private Especialidade especialidade;

    @Column(name = "crm", nullable = false)
    private String crm;

    @Column(name = "criadoEm", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ultimaAlteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = LocalDateTime.now();

}
