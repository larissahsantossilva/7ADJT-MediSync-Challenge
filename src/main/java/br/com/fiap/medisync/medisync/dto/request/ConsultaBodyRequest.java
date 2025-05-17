package br.com.fiap.medisync.medisync.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um nova Consulta")
@SchemaMapping("ConsultaInput")
public class ConsultaBodyRequest {

    @NotBlank(message = "O ID do Paciente é obrigatório")
    @Schema(description = "ID do Paciente associado", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idPaciente;

    @NotBlank(message = "O ID do Médico é obrigatório")
    @Schema(description = "ID do Médico associado", example = "123e4567-e89b-12d3-a456-426614174001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idMedico;

    @NotBlank(message = "O ID do Enfermeiro é obrigatório")
    @Schema(description = "ID do Enfermeiro associado", example = "123e4567-e89b-12d3-a456-426614174002", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idEnfermeiro;

    @NotBlank(message = "O ID da Unidade Básica de Saúde é obrigatório")
    @Schema(description = "ID da Unidade Básica de Saúde associada", example = "123e4567-e89b-12d3-a456-426614174003", requiredMode = Schema.RequiredMode.REQUIRED)
    private String idUnidadeBasicaSaude;

    @NotBlank(message = "A observação é obrigatória")
    @Schema(description = "Observação sobre a Consulta", example = "Check-up de rotina", requiredMode = Schema.RequiredMode.REQUIRED)
    private String observacao;

    @NotBlank(message = "A data da Consulta é obrigatória")
    @Schema(description = "Data e hora da Consulta", example = "2023-10-01T10:00:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String dataConsulta;
}