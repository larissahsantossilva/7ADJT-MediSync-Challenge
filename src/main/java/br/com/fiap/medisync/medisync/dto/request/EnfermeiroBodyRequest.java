package br.com.fiap.medisync.medisync.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo Enfermeiro")
public class EnfermeiroBodyRequest {

    @NotNull(message = "Usuário é obrigatório")
    @Schema(description = "Dados do usuário associado ao Enfermeiro", requiredMode = REQUIRED)
    private UsuarioBodyRequest usuario;

    @NotNull(message = "Especialidade é obrigatória")
    @Schema(description = "Especiadade associada ao Enfermeiro", requiredMode = REQUIRED)
    private EspecialidadeBodyRequest especialidade;

    @NotBlank(message = "COREN do Enfermeiro não pode ser vazio.")
    @NotNull(message = "COREN do Enfermeiro não pode ser nulo.")
    @Schema(description = "COREN do Enfermeiro", example = "COREN-SP-67890", requiredMode = REQUIRED)
    @Size(min = 1, max = 50)
    private String coren;

}
