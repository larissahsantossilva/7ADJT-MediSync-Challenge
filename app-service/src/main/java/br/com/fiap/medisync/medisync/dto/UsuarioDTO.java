package br.com.fiap.medisync.medisync.dto;

import br.com.fiap.medisync.medisync.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para retorno dos dados de um usuário")
public class UsuarioDTO {

    @Schema(description = "ID do usuário", example = "a1b2c3d4-e5f6-7890-abcd-1234567890ab")
    private UUID id;

    @Schema(description = "Nome completo do usuário", example = "João da Silva")
    private String nome;

    @Schema(description = "Email do usuário", example = "joao@email.com")
    private String email;

    @Schema(description = "Login do usuário", example = "joaosilva")
    private String login;

    @Schema(description = "CPF do usuário", example = "12345678901")
    private String cpf;

    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01")
    private LocalDate dataNascimento;

    @Schema(description = "Telefone do usuário", example = "11999999999")
    private String telefone;


    public UsuarioDTO(Usuario usuario){
            this.id = usuario.getId();
            this.nome = usuario.getNome();
            this.email = usuario.getEmail();
            this.login = usuario.getLogin();
            this.cpf = usuario.getCpf();
            this.dataNascimento = usuario.getDataNascimento();
            this.telefone = usuario.getTelefone();
    }

}