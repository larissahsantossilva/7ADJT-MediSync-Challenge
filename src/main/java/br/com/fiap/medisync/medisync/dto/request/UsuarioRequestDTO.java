package br.com.fiap.medisync.medisync.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criação de um novo usuário")
public class UsuarioRequestDTO {

        @NotBlank(message = "Nome do usuário não pode ser vazio.")
        @NotNull(message = "Nome do usuário não pode ser nulo.")
        @Schema(description = "Nome completo do usuário", example = "João da Silva", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(min = 1, max = 200)
        private String nome;

        @Email(message = "Email inválido")
        @NotBlank(message = "Email não pode ser vazio.")
        @NotNull(message = "Email não pode ser nulo.")
        @Schema(description = "Email do usuário", example = "joao@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
        private String email;

        @NotBlank(message = "Login não pode ser vazio.")
        @NotNull(message = "Login não pode ser nulo.")
        @Schema(description = "Login do usuário", example = "joaosilva", requiredMode = Schema.RequiredMode.REQUIRED)
        private String login;

        @NotBlank(message = "Senha não pode ser vazia.")
        @NotNull(message = "Senha não pode ser nula.")
        @Schema(description = "Senha do usuário", example = "senhaSegura123", requiredMode = Schema.RequiredMode.REQUIRED)
        private String senha;

        @NotBlank(message = "CPF não pode ser vazio.")
        @NotNull(message = "CPF não pode ser nulo.")
        @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
        @Schema(description = "CPF do usuário sem pontos ou traços", example = "12345678901", requiredMode = Schema.RequiredMode.REQUIRED)
        private String cpf;

        @NotBlank(message = "Data de nascimento não pode ser vazia.")
        @NotNull(message = "Data de nascimento não pode ser nula.")
        @Schema(description = "Data de nascimento do usuário", example = "1990-01-01", requiredMode = Schema.RequiredMode.REQUIRED)
        private LocalDate dataNascimento;

        @Schema(description = "Telefone do usuário com DDD", example = "11999999999", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        private String telefone;
}