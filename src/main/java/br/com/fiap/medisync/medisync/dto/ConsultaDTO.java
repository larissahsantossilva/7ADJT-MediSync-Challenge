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
    private String observacao;
    private String dataConsulta;
    private String status;
    private String criadoEm;
    private String ultimaAlteracao;


    public ConsultaDTO(Consulta consulta) {
        this.id = String.valueOf(consulta.getId());
        this.observacao = consulta.getObservacao();
        this.dataConsulta = consulta.getDataConsulta().format(FORMATTER);
        this.status = consulta.getStatus().name();
        this.criadoEm = consulta.getCriadoEm().format(FORMATTER);
        this.ultimaAlteracao = consulta.getUltimaAlteracao().format(FORMATTER);
    }
}
