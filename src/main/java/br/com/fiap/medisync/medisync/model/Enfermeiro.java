package br.com.fiap.medisync.medisync.model;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "enfermeiro", schema = "medisync")
public class Enfermeiro {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false, unique = true, foreignKey = @ForeignKey(name = "fk_enfermeiro_usuario"))
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idEspecialidade", foreignKey = @ForeignKey(name = "fk_enfermeiro_especialidade"))
    private Especialidade especialidade;

    @Column(name = "coren", nullable = false)
    private String coren;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = now();
}
