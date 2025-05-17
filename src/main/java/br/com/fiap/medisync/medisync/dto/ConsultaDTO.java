package br.com.fiap.medisync.medisync.dto;

import br.com.fiap.medisync.medisync.model.Consulta;
import lombok.*;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.format.DateTimeFormatter;

@SchemaMapping("Consulta")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaDTO {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String id;
    private String nomePaciente;
    private String cpfPaciente;
    private String nomeMedico;
    private String crmMedico;
    private String descricaoEspecialidadeMedico;
    private String nomeEnfermeiro;
    private String corenEnfermeiro;
    private String descricaoEspecialidadeEnfermeiro;
    private String nomeUnidadeBasicaSaude;
    private String endereco;
    private String observacao;
    private String dataConsulta;
    private String status;
    private String criadoEm;
    private String ultimaAlteracao;

    public ConsultaDTO(Consulta consulta) {
        this.id = String.valueOf(consulta.getId());
        this.nomePaciente = consulta.getPaciente().getUsuario().getNome();
        this.cpfPaciente = consulta.getPaciente().getUsuario().getCpf();
        this.nomeMedico = consulta.getMedico().getUsuario().getNome();
        this.crmMedico = consulta.getMedico().getCrm();
        this.descricaoEspecialidadeMedico = consulta.getMedico().getEspecialidade().getDescricao();
        this.nomeEnfermeiro = consulta.getEnfermeiro().getUsuario().getNome();
        this.corenEnfermeiro = consulta.getEnfermeiro().getCoren();
        this.descricaoEspecialidadeEnfermeiro = consulta.getEnfermeiro().getEspecialidade().getDescricao();
        this.nomeUnidadeBasicaSaude = consulta.getUnidadeSaude().getNome();
        this.endereco = consulta.getUnidadeSaude().getEndereco();
        this.observacao = consulta.getObservacao();
        this.dataConsulta = consulta.getDataConsulta().format(FORMATTER);
        this.status = consulta.getStatus().name();
        this.criadoEm = consulta.getCriadoEm().format(FORMATTER);
        this.ultimaAlteracao = consulta.getUltimaAlteracao().format(FORMATTER);
    }
}
