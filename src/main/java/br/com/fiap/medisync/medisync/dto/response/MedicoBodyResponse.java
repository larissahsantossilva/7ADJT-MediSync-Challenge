package br.com.fiap.medisync.medisync.dto.response;

import br.com.fiap.medisync.medisync.dto.UsuarioDTO;
import br.com.fiap.medisync.medisync.model.Medico;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para retorno dos dados de um médico")
public class MedicoBodyResponse {

    @Schema(description = "ID do médico", example = "b1c2f964-8e2f-4de8-812b-7ac86e1a3e53")
    private UUID id;

    @Schema(description = "Dados do usuário associado")
    private UsuarioDTO usuario;

    @Schema(description = "CRM do médico")
    private String crm;

    public MedicoBodyResponse(Medico medico) {
        this.id = medico.getId();
        this.usuario = medico.getUsuario() != null ? new UsuarioDTO(medico.getUsuario()) : null;
        this.crm = medico.getCrm();
    }

}
