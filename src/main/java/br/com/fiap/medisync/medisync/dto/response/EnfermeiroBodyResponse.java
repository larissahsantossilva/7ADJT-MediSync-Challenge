package br.com.fiap.medisync.medisync.dto.response;

import java.util.UUID;

import br.com.fiap.medisync.medisync.dto.EspecialidadeDTO;
import br.com.fiap.medisync.medisync.dto.UsuarioDTO;
import br.com.fiap.medisync.medisync.model.Enfermeiro;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para retorno dos dados de um Enfermeiro")
public class EnfermeiroBodyResponse {

    @Schema(description = "ID do Enfermeiro", example = "b1c2f964-8e2f-4de8-812b-7ac86e1a3e53")
    private UUID id;

    @Schema(description = "Dados do usuário associado")
    private UsuarioDTO usuario;
    
    @Schema(description = "Especialidade do enfermeiro associado")
    private EspecialidadeDTO especialidade;
    
    @Schema(description = "Coren do Enfermeiro", example = "COREN-SP-67890")
    private String coren;

    public EnfermeiroBodyResponse(Enfermeiro enfermeiro) {
        this.id = enfermeiro.getId();
        this.usuario = enfermeiro.getUsuario() != null ? new UsuarioDTO(enfermeiro.getUsuario()) : null;
        this.especialidade = enfermeiro.getEspecialidade() != null ? new EspecialidadeDTO(enfermeiro.getEspecialidade().getId(), enfermeiro.getEspecialidade().getDescricao()) : null;
        this.coren = enfermeiro.getCoren();
    }
}
