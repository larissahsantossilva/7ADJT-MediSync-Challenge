package br.com.fiap.medisync.medisync.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo médico")
public class MedicoBodyRequest {

    @NotNull(message = "Usuário é obrigatório")
    @Schema(description = "Dados do usuário associado ao paciente", requiredMode = Schema.RequiredMode.REQUIRED)
    private UsuarioBodyRequest usuario;

    @NotNull(message = "Especialidade é obrigatória")
    @Schema(description = "Especiadade associada ao médico", requiredMode = Schema.RequiredMode.REQUIRED)
    private EspecialidadeBodyRequest especialidade;

    @NotBlank(message = "CRM do médico não pode ser vazio.")
    @NotNull(message = "CRM do médico não pode ser nulo.")
    @Schema(description = "CRM do médico", example = "3712", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 50)
    private String crm;

}
