package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.request.MedicoBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.MedicoBodyResponse;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Medico;
import br.com.fiap.medisync.medisync.service.MedicoService;
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
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.convertToMedico;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping(MedicoController.V1_MEDICO)
@AllArgsConstructor
@Tag(name = "MedicoController", description = "Controller para CRUD de médicos.")
public class MedicoController {

    public static final String V1_MEDICO = "/api/v1/medicos";
    private static final Logger logger = getLogger(MedicoController.class);
    private final MedicoService medicoService;

    @Operation(
            description = "Busca todos os médicos de forma paginada.",
            summary = "Busca todos os médicos de forma paginada.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<MedicoBodyResponse>> listarMedicos(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        logger.info("GET | {} | Iniciado listarMedicos", V1_MEDICO);
        Page<Medico> medicos = this.medicoService.listarMedicos(page, size);
        List<MedicoBodyResponse> medicoResponses = medicos.stream()
                .map(MedicoBodyResponse::new)
                .toList();
        logger.info("GET | {} | Finalizado listarMedicos", V1_MEDICO);
        return ok(medicoResponses);
    }


    @Operation(
            description = "Busca médico por id.",
            summary = "Busca médico por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Medico.class))
                    ),
                    @ApiResponse(
                            description = NOT_FOUND,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MedicoBodyResponse> buscarMedicoPorId(@PathVariable("id") UUID id) {
        logger.info("GET | {} | Iniciado buscarMedicoPorId | id: {}", V1_MEDICO, id);
        var medico = medicoService.buscarMedicoPorId(id);
        if (medico != null) {
            logger.info("GET | {} | Finalizado buscarMedicoPorId | id: {}", V1_MEDICO, id);
            return ok(new MedicoBodyResponse(medico));
        }
        logger.info("GET | {} | Finalizado √ No Content | id: {}", V1_MEDICO, id);
        return status(HttpStatus.NOT_FOUND).build();
    }


    @Operation(
            description = "Cria médico.",
            summary = "Cria médico.",
            responses = {
                    @ApiResponse(
                            description = MEDICO_CRIADO_COM_SUCESSO,
                            responseCode = HTTP_STATUS_CODE_201,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_CRIAR_MEDICO,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<UUID> criarMedico(@Valid @RequestBody MedicoBodyRequest medicoBodyRequest) {
        logger.info("POST | {} | Iniciado criarMedico | Medico: {}", V1_MEDICO, medicoBodyRequest.getUsuario().getNome());
        Medico medico = medicoService.criarMedico(convertToMedico(medicoBodyRequest));
        logger.info("POST | {} | Finalizado criarMedico", V1_MEDICO);
        return status(201).body(medico.getId());
    }


    @Operation(
            description = "Atualiza médico por id.",
            summary = "Atualiza médico por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = MEDICO_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_ALTERAR_MEDICO,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMedico(@PathVariable("id") UUID id, @Valid @RequestBody MedicoBodyRequest medicoBodyRequest) {
        logger.info("PUT | {} | Iniciado atualizarMedico | id: {}", V1_MEDICO, id);
        medicoService.atualizarMedico(convertToMedico(medicoBodyRequest), id);
        logger.info("PUT | {} | Finalizado atualizarMedico", V1_MEDICO);
        return ok("Médico atualizado com sucesso");
    }


    @Operation(
            description = "Exclui médico por id.",
            summary = "Exclui médico por id.",
            responses = {
                    @ApiResponse(
                            description = NO_CONTENT,
                            responseCode = HTTP_STATUS_CODE_204,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = MEDICO_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirMedicoPorId(@PathVariable("id") UUID id) {
        logger.info("DELETE | {} | Iniciado excluirMedicoPorId | id: {}", V1_MEDICO, id);
        try {
            medicoService.excluirMedicoPorId(id);
            logger.info("DELETE | {} | Médico excluído com sucesso | Id: {}", V1_MEDICO, id);
            return ResponseEntity.noContent().build();
        } catch (UnprocessableEntityException e) {
            logger.error("DELETE | {} | Erro ao excluir médico | Id: {} | Erro: {}", V1_MEDICO, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado");
        }
    }

}
