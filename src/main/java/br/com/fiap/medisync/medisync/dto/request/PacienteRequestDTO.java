package br.com.fiap.medisync.medisync.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo paciente")
public class PacienteRequestDTO {

        @NotNull(message = "Usuário é obrigatório")
        @Schema(description = "Dados do usuário associado ao paciente", requiredMode = Schema.RequiredMode.REQUIRED)
        private UsuarioRequestDTO usuario;

}