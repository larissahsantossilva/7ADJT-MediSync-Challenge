package br.com.fiap.medisync.medisync.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Schema(description = "DTO para retorno de criação de uma Consulta")
@SchemaMapping("CriarConsultaResponse")
public class CriarConsultaResponse {

    @Schema(description = "Identificador único da Consulta criada", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID consultaId;

    @Schema(description = "Status da operação", example = "Consulta criada com sucesso")
    private String message;
}
