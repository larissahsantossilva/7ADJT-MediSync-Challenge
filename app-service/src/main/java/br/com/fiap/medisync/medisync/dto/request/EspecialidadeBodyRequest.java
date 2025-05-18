package br.com.fiap.medisync.medisync.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo médico")
public class EspecialidadeBodyRequest {

    @NotBlank(message = "Descrição não pode ser vazio.")
    @NotNull(message = "Descrição não pode ser nulo.")
    @Schema(description = "Descrição do médico", example = "Pediatria", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 1, max = 100)
    private String descricao;

}
