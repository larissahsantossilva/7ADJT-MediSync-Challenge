package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.dto.NotificacaoEventoDTO;
import br.com.fiap.medisync.medisync.dto.request.AtualizarConsultaBodyRequest;
import br.com.fiap.medisync.medisync.dto.request.CriarConsultaBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.AtualizarConsultaResponse;
import br.com.fiap.medisync.medisync.dto.response.CriarConsultaResponse;
import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.*;
import br.com.fiap.medisync.medisync.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.configuration.RabbitMQConfig.EXCHANGE;
import static br.com.fiap.medisync.medisync.configuration.RabbitMQConfig.ROUTING_KEY;
import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.uuidValidator;
import static java.lang.String.valueOf;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class ConsultaService {

    private static final Logger logger = getLogger(ConsultaService.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    private final ConsultaRepository consultaRepository;
    private final EnfermeiroRepository enfermeiroRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final DateTimeFormatter formatterMessageData = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH 'horas.'");

    public Page<Consulta> listarConsultas(int pagina, int tamanho) {
        Page<Consulta> all = consultaRepository.findAll(PageRequest.of(pagina, tamanho));
        logger.info("Listando consultas com pagina {} e tamanho {}", pagina, tamanho);
        logger.info("Consultas encontradas: {}", all);
        return all;
    }

    public Consulta buscarConsultaPorId(UUID id) {
        uuidValidator(id);
        return consultaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CONSULTA_NAO_ENCONTRADA));
    }

    public CriarConsultaResponse criarConsulta(CriarConsultaBodyRequest consulta) {
        try {
            uuidValidator(UUID.fromString(consulta.getIdPaciente()));
            uuidValidator(UUID.fromString(consulta.getIdMedico()));
            uuidValidator(UUID.fromString(consulta.getIdEnfermeiro()));
            uuidValidator(UUID.fromString(consulta.getIdUnidadeBasicaSaude()));
            Optional<Paciente> paciente = pacienteRepository.findById(UUID.fromString(consulta.getIdPaciente()));
            Optional<Medico> medico = medicoRepository.findById(UUID.fromString(consulta.getIdMedico()));
            Optional<Enfermeiro> enfermeiro = enfermeiroRepository.findById(UUID.fromString(consulta.getIdEnfermeiro()));
            Optional<UnidadeSaude> unidadeSaude = unidadeSaudeRepository.findById(UUID.fromString(consulta.getIdUnidadeBasicaSaude()));
            Consulta consultaAgendada = Consulta.builder()
                    .paciente(paciente.orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO)))
                    .medico(medico.orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO)))
                    .enfermeiro(enfermeiro.orElseThrow(() -> new ResourceNotFoundException(ENFERMEIRO_NAO_ENCONTRADO)))
                    .unidadeSaude(unidadeSaude.orElseThrow(() -> new ResourceNotFoundException(UNIDADE_BASICA_SAUDE_NAO_ENCONTRADO)))
                    .observacao(consulta.getObservacao())
                    .dataConsulta(LocalDateTime.parse(consulta.getDataConsulta(), formatter))
                    .criadoEm(LocalDateTime.now())
                    .ultimaAlteracao(LocalDateTime.now())
                    .status(ConsultaStatus.AGENDADA)
                .build();
            Consulta consultaAgendadaCriada = consultaRepository.save(consultaAgendada);
            CriarConsultaResponse consultaCriadaComSucesso = CriarConsultaResponse.builder()
                    .consultaId(consultaAgendadaCriada.getId())
                    .message("Consulta criada com sucesso")
                .build();
            enviarMensagemRabbitMQ(consultaAgendadaCriada);
            logger.info("Consulta criada com sucesso: {}", consultaCriadaComSucesso);
            return consultaCriadaComSucesso;
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_CONSULTA, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_CONSULTA);
        }
    }

    public AtualizarConsultaResponse atualizarConsulta(UUID id, AtualizarConsultaBodyRequest consulta) {
        try {
            uuidValidator(id);
            uuidValidator(UUID.fromString(consulta.getIdPaciente()));
            uuidValidator(UUID.fromString(consulta.getIdMedico()));
            uuidValidator(UUID.fromString(consulta.getIdEnfermeiro()));
            uuidValidator(UUID.fromString(consulta.getIdUnidadeBasicaSaude()));
            Consulta consultaExistente = consultaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CONSULTA_NAO_ENCONTRADA));
            Optional<Paciente> paciente = pacienteRepository.findById(UUID.fromString(consulta.getIdPaciente()));
            Optional<Medico> medico = medicoRepository.findById(UUID.fromString(consulta.getIdMedico()));
            Optional<Enfermeiro> enfermeiro = enfermeiroRepository.findById(UUID.fromString(consulta.getIdEnfermeiro()));
            Optional<UnidadeSaude> unidadeSaude = unidadeSaudeRepository.findById(UUID.fromString(consulta.getIdUnidadeBasicaSaude()));
            Consulta consultaAgendada = Consulta.builder()
                    .id(consultaExistente.getId())
                    .paciente(paciente.orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO)))
                    .medico(medico.orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO)))
                    .enfermeiro(enfermeiro.orElseThrow(() -> new ResourceNotFoundException(ENFERMEIRO_NAO_ENCONTRADO)))
                    .unidadeSaude(unidadeSaude.orElseThrow(() -> new ResourceNotFoundException(UNIDADE_BASICA_SAUDE_NAO_ENCONTRADO)))
                    .observacao(consulta.getObservacao())
                    .dataConsulta(LocalDateTime.parse(consulta.getDataConsulta(), formatter))
                    .criadoEm(LocalDateTime.now())
                    .ultimaAlteracao(LocalDateTime.now())
                    .status(ConsultaStatus.valueOf(consulta.getStatus()))
                .build();
            Consulta consultaAtualizada = consultaRepository.save(consultaAgendada);
            AtualizarConsultaResponse consultaAtualizadaComSucesso = AtualizarConsultaResponse.builder()
                    .consultaId(consultaAtualizada.getId())
                    .message("Consulta atualizada com sucesso")
                .build();
            enviarMensagemRabbitMQ(consultaAtualizada);
            logger.info("Consulta atualizada com sucesso: {}", consultaAtualizadaComSucesso);
            return consultaAtualizadaComSucesso;
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_ALTERAR_CONSULTA, e);
            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_CONSULTA);
        }
    }

    private void enviarMensagemRabbitMQ(Consulta consultaAtualizada) {
        NotificacaoEventoDTO evento = new NotificacaoEventoDTO(
            consultaAtualizada.getId(),
            consultaAtualizada.getPaciente().getId(),
            consultaAtualizada.getStatus(),
            consultaAtualizada.getDataConsulta(),
            "Sua consulta está " + valueOf(consultaAtualizada.getStatus()).toLowerCase() + " para o dia " + consultaAtualizada.getDataConsulta().format(formatterMessageData)
        );
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, evento);
        logger.info("Notificação enviada para RabbitMQ com sucesso: {}", evento);
    }
}
