package br.com.fiap.medisync.medisync.dto.response;

import br.com.fiap.medisync.medisync.dto.UsuarioDTO;
import br.com.fiap.medisync.medisync.model.Paciente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para retorno dos dados de um paciente")
public class PacienteResponseDTO {

        @Schema(description = "ID do paciente", example = "b1c2f964-8e2f-4de8-812b-7ac86e1a3e53")
        private UUID id;

        @Schema(description = "Dados do usuário associado")
        private UsuarioDTO usuario;

        @Schema(description = "Data de criação do paciente", example = "2025-04-26")
        private LocalDateTime criadoEm;

        @Schema(description = "Data da última alteração do paciente", example = "2025-04-26")
        private LocalDateTime ultimaAlteracao;

        public PacienteResponseDTO(Paciente paciente) {
                this.id = paciente.getId();
                this.usuario = paciente.getUsuario() != null ? new UsuarioDTO(paciente.getUsuario()) : null;
                this.criadoEm = paciente.getCriadoEm();
                this.ultimaAlteracao = paciente.getUltimaAlteracao();
        }
}
