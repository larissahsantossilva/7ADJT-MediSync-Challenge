package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.model.Consulta;
import br.com.fiap.medisync.medisync.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.uuidValidator;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class ConsultaService {

    private static final Logger logger = getLogger(ConsultaService.class);
    
    private final ConsultaRepository consultaRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public Consulta buscarConsultaPorId(UUID id) {
        uuidValidator(id);
        return consultaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CONSULTA_NAO_ENCONTRADA));
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
