package br.com.fiap.medisync.medisync.controller;

import br.com.fiap.medisync.medisync.dto.request.EnfermeiroBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.EnfermeiroBodyResponse;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Enfermeiro;
import br.com.fiap.medisync.medisync.service.EnfermeiroService;
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
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.convertToEnfermeiro;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;


@RestController
@RequestMapping(EnfermeiroController.V1_ENFERMEIRO)
@AllArgsConstructor
@Tag(name = "EnfermeiroController", description = "Controller para CRUD de Enfermeiros.")
public class EnfermeiroController {

    public static final String V1_ENFERMEIRO = "/api/v1/enfermeiros";
    private static final Logger logger = getLogger(EnfermeiroController.class);
    private final EnfermeiroService enfermeiroService;

    @Operation(
            description = "Busca todos os Enfermeiros de forma paginada.",
            summary = "Busca todos os Enfermeiros de forma paginada.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Enfermeiro.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<EnfermeiroBodyResponse>> listarEnfermeiros(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        logger.info("GET | {} | Iniciado listarEnfermeiros", V1_ENFERMEIRO);
        Page<Enfermeiro> enfermeiros = this.enfermeiroService.listarEnfermeiros(page, size);
        List<EnfermeiroBodyResponse> enfermeiroResponses = enfermeiros.stream()
                .map(EnfermeiroBodyResponse::new)
                .toList();
        logger.info("GET | {} | Finalizado listarEnfermeiros", V1_ENFERMEIRO);
        return ok(enfermeiroResponses);
    }

    @Operation(
            description = "Busca enfermeiro por id.",
            summary = "Busca enfermeiro por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Enfermeiro.class))
                    ),
                    @ApiResponse(
                            description = NOT_FOUND,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EnfermeiroBodyResponse> buscarEnfermeiroPorId(@PathVariable("id") UUID id) {
        logger.info("GET | {} | Iniciado buscarEnfermeiroPorId | id: {}", V1_ENFERMEIRO, id);
        var enfermeiro = enfermeiroService.buscarEnfermeiroPorId(id);
        if (enfermeiro != null) {
            logger.info("GET | {} | Finalizado buscarEnfermeiroPorId | id: {}", V1_ENFERMEIRO, id);
            return ok(new EnfermeiroBodyResponse(enfermeiro));
        }
        logger.info("GET | {} | Finalizado √ No Content | id: {}", V1_ENFERMEIRO, id);
        return status(HttpStatus.NOT_FOUND).build();
    }


    @Operation(
            description = "Cria Enfermeiro.",
            summary = "Cria Enfermeiro.",
            responses = {
                    @ApiResponse(
                            description = ENFERMEIRO_CRIADO_COM_SUCESSO,
                            responseCode = HTTP_STATUS_CODE_201,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_CRIAR_ENFERMEIRO,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<UUID> criarEnfermeiro(@Valid @RequestBody EnfermeiroBodyRequest enfermeiroBodyRequest) {
        logger.info("POST | {} | Iniciado criarEnfermeiro | Enfermeiro: {}", V1_ENFERMEIRO, enfermeiroBodyRequest.getUsuario().getNome());
        Enfermeiro enfermeiro = enfermeiroService.criarEnfermeiro(convertToEnfermeiro(enfermeiroBodyRequest));
        logger.info("POST | {} | Finalizado criarEnfermeiro", V1_ENFERMEIRO);
        return status(201).body(enfermeiro.getId());
    }


    @Operation(
            description = "Atualiza Enfermeiro por id.",
            summary = "Atualiza Enfermeiro por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = ENFERMEIRO_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_ALTERAR_ENFERMEIRO,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarEnfermeiro(@PathVariable("id") UUID id, @Valid @RequestBody EnfermeiroBodyRequest enfermeiroBodyRequest) {
        logger.info("PUT | {} | Iniciado atualizarEnfermeiro | id: {}", V1_ENFERMEIRO, id);
        enfermeiroService.atualizarEnfermeiro(convertToEnfermeiro(enfermeiroBodyRequest), id);
        logger.info("PUT | {} | Finalizado atualizarEnfermeiro", V1_ENFERMEIRO);
        return ok("Enfermeiro atualizado com sucesso");
    }


    @Operation(
            description = "Exclui Enfermeiro por id.",
            summary = "Exclui Enfermeiro por id.",
            responses = {
                    @ApiResponse(
                            description = NO_CONTENT,
                            responseCode = HTTP_STATUS_CODE_204,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = ENFERMEIRO_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEnfermeiroPorId(@PathVariable("id") UUID id) {
        logger.info("DELETE | {} | Iniciado excluirEnfermeiroPorId | id: {}", V1_ENFERMEIRO, id);
        try {
            enfermeiroService.excluirEnfermeiroPorId(id);
            logger.info("DELETE | {} | Enfermeiro excluído com sucesso | Id: {}", V1_ENFERMEIRO, id);
            return ResponseEntity.noContent().build();
        } catch (UnprocessableEntityException e) {
            logger.error("DELETE | {} | Erro ao excluir Enfermeiro | Id: {} | Erro: {}", V1_ENFERMEIRO, id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enfermeiro não encontrado");
        }
    }
}
