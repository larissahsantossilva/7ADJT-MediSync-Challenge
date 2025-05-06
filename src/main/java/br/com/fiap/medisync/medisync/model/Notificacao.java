package br.com.fiap.medisync.medisync.model;

import static jakarta.persistence.GenerationType.AUTO;
import static java.time.LocalDateTime.now;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificacao", schema = "medisync")
public class Notificacao {
	
	@Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", nullable = false, unique = true)
	private UUID id;

	@Column(name = "id_paciente", nullable = false)
	private UUID idPaciente;

	@Column(name = "id_consulta", nullable = false)
	private UUID idConsulta;

	@Column(name = "mensagem", nullable = false)
	private String mensagem;

	@Column(name = "data_envio", nullable = false)
	private LocalDate dataEnvio;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = now();
}
