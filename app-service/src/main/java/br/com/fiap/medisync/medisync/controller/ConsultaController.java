package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.ConsultaDTO;
import br.com.fiap.medisync.medisync.dto.request.AtualizarConsultaBodyRequest;
import br.com.fiap.medisync.medisync.dto.request.CriarConsultaBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.AtualizarConsultaResponse;
import br.com.fiap.medisync.medisync.dto.response.CriarConsultaResponse;
import br.com.fiap.medisync.medisync.model.Consulta;
import br.com.fiap.medisync.medisync.service.ConsultaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ConsultaController {

  private final ConsultaService consultaService;
  private static final Logger logger = LoggerFactory.getLogger(ConsultaController.class);

  @QueryMapping
  public ConsultaDTO buscarConsultaPorId(@Argument UUID id){
    logger.info("Buscando consulta com id: {}", id);
    Consulta consulta = consultaService.buscarConsultaPorId(id);
    return new ConsultaDTO(consulta);
  }

  @QueryMapping
  public List<ConsultaDTO> listarConsultas(@Argument int pagina, @Argument int tamanho) {
    logger.info("Listando consulta com pagina {} e tamanho {}", pagina, tamanho);
    if (pagina < 0) {
      pagina = 0;
    }
    if (tamanho <= 0) {
      tamanho = 10;
    }
    Page<Consulta> consultas = consultaService.listarConsultas(pagina, tamanho);
    List<ConsultaDTO> list = consultas.stream().map(ConsultaDTO::new).toList();
    logger.info("Consultas encontradas: {}", list);
    return list;
  }

  @MutationMapping
  public CriarConsultaResponse criarConsulta(@Argument CriarConsultaBodyRequest consulta) {
    logger.info("Criando consulta com bodyRequest: {}", consulta);
    return consultaService.criarConsulta(consulta);
  }

  @MutationMapping
  public AtualizarConsultaResponse atualizarConsulta(@Argument UUID id, @Argument AtualizarConsultaBodyRequest consulta) {
    logger.info("Atualizando consulta com id {} bodyRequest: {}", id, consulta);
    return consultaService.atualizarConsulta(id, consulta);
  }
}
