package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.ConsultaDTO;
import br.com.fiap.medisync.medisync.dto.request.ConsultaBodyRequest;
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
import org.springframework.web.bind.annotation.RequestParam;

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
  public List<ConsultaDTO> listarConsultas(){
    List<Consulta> consultas = consultaService.listarConsultas();
    return consultas.stream().map(ConsultaDTO::new).toList();
  }

  @MutationMapping
  public CriarConsultaResponse criarConsulta(@Argument ConsultaBodyRequest consulta) {
    logger.info("Criando consulta com bodyRequest: {}", consulta);
    return consultaService.criarConsulta(consulta);
  }
}
