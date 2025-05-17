package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.dto.request.ConsultaBodyRequest;
import br.com.fiap.medisync.medisync.dto.response.CriarConsultaResponse;
import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.*;
import br.com.fiap.medisync.medisync.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.uuidValidator;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class ConsultaService {

    private static final Logger logger = getLogger(ConsultaService.class);
    
    private final ConsultaRepository consultaRepository;
    private final EnfermeiroRepository enfermeiroRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public Consulta buscarConsultaPorId(UUID id) {
        uuidValidator(id);
        return consultaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CONSULTA_NAO_ENCONTRADA));
    }

    public CriarConsultaResponse criarConsulta(ConsultaBodyRequest consulta) {
        try {
            logger.info("Id medico: {}", consulta.getIdMedico());
            logger.info("Consulta encontrada: {}", consulta);
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
            logger.info("Consulta agendada: {}", consultaAgendada);
            Consulta consultaAgendadaCriada = consultaRepository.save(consultaAgendada);
            logger.info("Consulta consultaAgendada criada: {}", consultaAgendada);
            CriarConsultaResponse consultaCriadaComSucesso = CriarConsultaResponse.builder()
                    .consultaId(consultaAgendadaCriada.getId())
                    .statusMessage("Consulta criada com sucesso")
                .build();
            logger.info("Consulta criada com sucesso: {}", consultaCriadaComSucesso);
            return consultaCriadaComSucesso;
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_CONSULTA, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_CONSULTA);
        }
    }

//    public Consulta criarConsulta(Consulta consulta) {
//        try {
//            Especialidade especialidadePersistida = especialidadeRepository
//                    .findByDescricao(enfermeiro.getEspecialidade().getDescricao())
//                    .orElseGet(() -> especialidadeRepository.save(enfermeiro.getEspecialidade()));
//
//            enfermeiro.setEspecialidade(especialidadePersistida);
//
//            Usuario usuarioPersistido = usuarioRepository
//                    .findByCpf(enfermeiro.getUsuario().getCpf())
//                    .orElseGet(() -> usuarioRepository.save(enfermeiro.getUsuario()));
//
//            enfermeiro.setUsuario(usuarioPersistido);
//
//            return consultaRepository.save(enfermeiro);
//        } catch (DataAccessException e) {
//            logger.error(ERRO_AO_CRIAR_ENFERMEIRO, e);
//            throw new UnprocessableEntityException(ERRO_AO_CRIAR_ENFERMEIRO);
//        }
//    }
//
//    public Enfermeiro atualizarEnfermeiro(Enfermeiro enfermeiro, UUID id) {
//    	Enfermeiro enfermeiroExistente = buscarEnfermeiroPorId(id);
//        atualizarUsuarioEnfermeiro(enfermeiro, enfermeiroExistente);
//        atualizarEspecialidadeEnfermeiro(enfermeiro, enfermeiroExistente);
//        if (enfermeiroExistente.getCoren() != null) enfermeiroExistente.setCoren(enfermeiro.getCoren());
//        enfermeiroExistente.setUltimaAlteracao(now());
//        return saveEnfermeiro(enfermeiroExistente);
//    }
//
//	private void atualizarUsuarioEnfermeiro(Enfermeiro enfermeiro, Enfermeiro enfermeiroExistente) {
//		if (enfermeiro.getUsuario() != null) {
//            Usuario usuarioExistente = enfermeiroExistente.getUsuario();
//            Usuario usuarioAtualizado = enfermeiro.getUsuario();
//            if (usuarioAtualizado.getNome() != null) usuarioExistente.setNome(usuarioAtualizado.getNome());
//            if (usuarioAtualizado.getEmail() != null) usuarioExistente.setEmail(usuarioAtualizado.getEmail());
//            if (usuarioAtualizado.getLogin() != null) usuarioExistente.setLogin(usuarioAtualizado.getLogin());
//            if (usuarioAtualizado.getSenha() != null) usuarioExistente.setSenha(usuarioAtualizado.getSenha());
//            if (usuarioAtualizado.getCpf() != null) usuarioExistente.setCpf(usuarioAtualizado.getCpf());
//            if (usuarioAtualizado.getTelefone() != null) usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
//            if (usuarioAtualizado.getDataNascimento() != null) usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
//            if (usuarioAtualizado.getUltimaAlteracao() != null) usuarioExistente.setUltimaAlteracao(now());
//        }
//	}
//
//	private void atualizarEspecialidadeEnfermeiro(Enfermeiro enfermeiro, Enfermeiro enfermeiroExistente) {
//		if (enfermeiro.getEspecialidade() != null) {
//			Especialidade especialidadeExistente = enfermeiroExistente.getEspecialidade();
//			Especialidade especialidadeAtualizada = enfermeiro.getEspecialidade();
//			if (especialidadeAtualizada.getDescricao() != null) especialidadeExistente.setDescricao(especialidadeAtualizada.getDescricao());
//			if (especialidadeAtualizada.getUltimaAlteracao() != null) especialidadeExistente.setUltimaAlteracao(now());
//		}
//	}
//
//	private Enfermeiro saveEnfermeiro(Enfermeiro enfermeiroExistente) {
//		try {
//            return consultaRepository.save(enfermeiroExistente);
//        } catch (DataAccessException e) {
//            logger.error(ERRO_AO_ALTERAR_ENFERMEIRO, e);
//            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_ENFERMEIRO);
//        }
//	}
//
//    public void excluirEnfermeiroPorId(UUID id) {
//        uuidValidator(id);
//
//        Enfermeiro enfermeiro = consultaRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException(ENFERMEIRO_NAO_ENCONTRADO));
//
//        Usuario usuario = enfermeiro.getUsuario();
//        UUID usuarioId = usuario.getId();
//
//        try {
//            consultaRepository.deleteById(id);
//
//            boolean usuarioVinculadoAMedico = medicoRepository.existsByUsuarioId(usuarioId);
//            boolean usuarioVinculadoAPaciente = pacienteRepository.existsByUsuarioId(usuarioId);
//
//            if (!usuarioVinculadoAMedico && !usuarioVinculadoAPaciente) {
//                usuarioRepository.deleteById(usuarioId);
//            }
//        } catch (DataAccessException e) {
//            logger.error(ERRO_AO_DELETAR_ENFERMEIRO, e);
//            throw new UnprocessableEntityException(ERRO_AO_DELETAR_ENFERMEIRO);
//        }
//    }
}
