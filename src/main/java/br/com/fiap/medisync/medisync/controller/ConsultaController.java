package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.ConsultaDTO;
import br.com.fiap.medisync.medisync.model.Consulta;
import br.com.fiap.medisync.medisync.service.ConsultaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class ConsultaController {

  private final ConsultaService consultaService;
  
  @QueryMapping
  public ConsultaDTO buscarConsultaPorId(@Argument UUID id){
    Consulta consulta = consultaService.buscarConsultaPorId(id);
    return new ConsultaDTO(consulta);
  }

  @QueryMapping
  public List<ConsultaDTO> listarConsultas(){
    List<Consulta> consultas = consultaService.listarConsultas();
    return consultas.stream().map(ConsultaDTO::new).toList();
  }

  /*@QueryMapping
  public VehicleAndReservationDTO getVehicleAndReservarionById(@Argument Long id){
    return consultaService.getVehicleAndReservationById(id);
  }
  
  @MutationMapping
  public AddVehicleResponseDTO addVehicle(@Argument VehicleDTO vehicle) {
    return consultaService.addVehicle(vehicle);
  }*/
}
