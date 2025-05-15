package br.com.fiap.medisync.medisync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consulta", schema = "medisync")
public class Consulta {
    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPaciente", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_consulta_paciente"))
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMedico", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_consulta_medico"))
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEnfermeiro", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_consulta_enfermeiro"))
    private Enfermeiro enfermeiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnidadeSaude", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_consulta_unidade_saude"))
    private UnidadeSaude unidadeSaude;

    @Column(name = "observacao", nullable = true)
    private String observacao;

    @Column(name = "data_consulta", nullable = false)
    private LocalDateTime dataConsulta;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsultaStatus status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = LocalDateTime.now();
}
