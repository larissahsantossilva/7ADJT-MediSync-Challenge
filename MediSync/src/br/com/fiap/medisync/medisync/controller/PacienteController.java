package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.request.PacienteBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.PacienteBodyResponse;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Paciente;
import br.com.fiap.medisync.medisync.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.convertToPaciente;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(PacienteController.V1_PACIENTE)
@AllArgsConstructor
@Tag(name = "PacienteController", description = "Controller para CRUD de paciente.")
public class PacienteController {

    public static final String V1_PACIENTE = "/api/v1/pacientes";
    private static final Logger logger = getLogger(PacienteController.class);
    private final PacienteService pacienteService;

    @Operation(
            description = "Busca todos os pacientes de forma paginada.",
            summary = "Busca todos os pacientes de forma paginada.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<PacienteBodyResponse>> listarPacientes(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        logger.info("GET | {} | Iniciado listarPacientes", V1_PACIENTE);
        Page<Paciente> pacientes = this.pacienteService.listarPacientes(page, size);
        List<PacienteBodyResponse> pacienteResponses = pacientes.stream()
                .map(PacienteBodyResponse::new)
                .toList();
        logger.info("GET | {} | Finalizado listarPacientes", V1_PACIENTE);
        return ok(pacienteResponses);
    }


    @Operation(
            description = "Busca paciente por id.",
            summary = "Busca paciente por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paciente.class))
                    ),
                    @ApiResponse(
                            description = NOT_FOUND,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PacienteBodyResponse> buscarPacientePorId(@PathVariable("id") UUID id) {
        logger.info("GET | {} | Iniciado buscarPacientePorId | id: {}", V1_PACIENTE, id);
        var paciente = pacienteService.buscarPacientePorId(id);
        if (paciente != null) {
            logger.info("GET | {} | Finalizado buscarPacientePorId | id: {}", V1_PACIENTE, id);
            return ok(new PacienteBodyResponse(paciente));
        }
        logger.info("GET | {} | Finalizado √ No Content | id: {}", V1_PACIENTE, id);
        return status(HttpStatus.NOT_FOUND).build();
    }


   @Operation(
            description = "Cria paciente.",
            summary = "Cria paciente.",
            responses = {
                    @ApiResponse(
                            description = PACIENTE_CRIADO_COM_SUCESSO,
                            responseCode = HTTP_STATUS_CODE_201,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_CRIAR_PACIENTE,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<UUID> criarPaciente(@Valid @RequestBody PacienteBodyRequest pacienteBodyRequest) {
        logger.info("POST | {} | Iniciado criarPaciente | Paciente: {}", V1_PACIENTE, pacienteBodyRequest.getUsuario().getNome());
        Paciente paciente = pacienteService.criarPaciente(convertToPaciente(pacienteBodyRequest));
        logger.info("POST | {} | Finalizado criarPaciente", V1_PACIENTE);
        return status(201).body(paciente.getId());
    }


    @Operation(
            description = "Atualiza paciente por id.",
            summary = "Atualiza paciente por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = PACIENTE_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_ALTERAR_PACIENTE,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarPaciente(@PathVariable("id") UUID id, @Valid @RequestBody PacienteBodyRequest pacienteBodyRequest) {
        logger.info("PUT | {} | Iniciado atualizarPaciente | id: {}", V1_PACIENTE, id);
        pacienteService.atualizarPaciente(convertToPaciente(pacienteBodyRequest), id);
        logger.info("PUT | {} | Finalizado atualizarPaciente", V1_PACIENTE);
        return ok("Paciente atualizado com sucesso");
    }


    @Operation(
            description = "Exclui paciente por id.",
            summary = "Exclui paciente por id.",
            responses = {
                    @ApiResponse(
                            description = NO_CONTENT,
                            responseCode = HTTP_STATUS_CODE_204,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = PACIENTE_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirPacientePorId(@PathVariable("id") UUID id) {
        logger.info("DELETE | {} | Iniciado excluirPaciente | id: {}", V1_PACIENTE, id);
        try {
            pacienteService.excluirPacientePorId(id);
            logger.info("DELETE | {} | Paciente excluído com sucesso | Id: {}", V1_PACIENTE, id);
            return ResponseEntity.noContent().build();
        } catch (UnprocessableEntityException e) {
            logger.error("DELETE | {} | Erro ao excluir paciente | Id: {} | Erro: {}", V1_PACIENTE, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente não encontrado");
        }
    }

}