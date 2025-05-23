package br.com.fiap.medisync.notificacao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notificacao", schema = "medisync")
public class Notificacao {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
	private UUID id;

	@Column(name = "id_paciente", nullable = false)
	private UUID pacienteId;

	@Column(name = "id_consulta", nullable = false)
	private UUID consultaId;

	@Column(name = "mensagem", nullable = false)
	private String mensagem;

	@Column(name = "data_envio", nullable = false)
	private LocalDateTime dataEnvio;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm = now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = now();
}
