package br.com.fiap.medisync.medisync.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para retorno as especialidades")
public class EspecialidadeDTO {

    @Schema(description = "ID da especialidade", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ab")
    private UUID id;

    @Schema(description = "Descrição da especialidade", example = "Pediatria")
    private String descricao;

}
