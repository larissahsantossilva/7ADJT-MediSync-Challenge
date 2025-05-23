package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Especialidade;
import br.com.fiap.medisync.medisync.model.Medico;
import br.com.fiap.medisync.medisync.model.Usuario;
import br.com.fiap.medisync.medisync.repository.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.uuidValidator;
import static org.slf4j.LoggerFactory.getLogger;

@Service
@AllArgsConstructor
public class MedicoService {

    private static final Logger logger = getLogger(MedicoService.class);
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especialidadeRepository;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final EnfermeiroRepository enfermeiroRepository;


    public Page<Medico> listarMedicos(int page, int size) {
        return medicoRepository.findAll(PageRequest.of(page, size));
    }

    public Medico buscarMedicoPorId(UUID id) {
        uuidValidator(id);
        return medicoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ID_NAO_ENCONTRADO));
    }

    public Medico criarMedico(Medico medico) {
        try {

            Especialidade especialidadePersistida = especialidadeRepository
                    .findByDescricao(medico.getEspecialidade().getDescricao())
                    .orElseGet(() -> especialidadeRepository.save(medico.getEspecialidade()));

            medico.setEspecialidade(especialidadePersistida);

            Usuario usuarioPersistido = usuarioRepository
                    .findByCpf(medico.getUsuario().getCpf())
                    .orElseGet(() -> usuarioRepository.save(medico.getUsuario()));

            medico.setUsuario(usuarioPersistido);

            return medicoRepository.save(medico);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_MEDICO, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_MEDICO);
        }
    }

    public Medico atualizarMedico(Medico medico, UUID id) {
        Medico medicoExistente = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO));

        if (medico.getUsuario() != null) {
            Usuario usuarioExistente = medicoExistente.getUsuario();
            Usuario usuarioAtualizado = medico.getUsuario();

            if (usuarioAtualizado.getNome() != null) usuarioExistente.setNome(usuarioAtualizado.getNome());
            if (usuarioAtualizado.getEmail() != null) usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            if (usuarioAtualizado.getLogin() != null) usuarioExistente.setLogin(usuarioAtualizado.getLogin());
            if (usuarioAtualizado.getSenha() != null) usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            if (usuarioAtualizado.getCpf() != null) usuarioExistente.setCpf(usuarioAtualizado.getCpf());
            if (usuarioAtualizado.getTelefone() != null) usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
            if (usuarioAtualizado.getDataNascimento() != null) usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
            if (usuarioAtualizado.getUltimaAlteracao() != null) usuarioExistente.setUltimaAlteracao(LocalDateTime.now());
        }

        if (medico.getEspecialidade() != null) {
            Especialidade especialidadeExistente = medicoExistente.getEspecialidade();
            Especialidade especialidadeAtualizada = medico.getEspecialidade();

            if (especialidadeAtualizada.getDescricao() != null) especialidadeExistente.setDescricao(especialidadeAtualizada.getDescricao());
            if (especialidadeAtualizada.getUltimaAlteracao() != null) especialidadeExistente.setUltimaAlteracao(LocalDateTime.now());
        }

        if (medicoExistente.getCrm() != null) medicoExistente.setCrm(medico.getCrm());

        medicoExistente.setUltimaAlteracao(LocalDateTime.now());

        try {
            return medicoRepository.save(medicoExistente);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_ALTERAR_MEDICO, e);
            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_MEDICO);
        }
    }

    public void excluirMedicoPorId(UUID id) {
        uuidValidator(id);

        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MEDICO_NAO_ENCONTRADO));

        Usuario usuario = medico.getUsuario();
        UUID usuarioId = usuario.getId();

        try {
            medicoRepository.deleteById(id);

            boolean usuarioVinculadoAPaciente = pacienteRepository.existsByUsuarioId(usuarioId);
            boolean usuarioVinculadoAEnfermeiro = enfermeiroRepository.existsByUsuarioId(usuarioId);

            if (!usuarioVinculadoAPaciente && !usuarioVinculadoAEnfermeiro) {
                usuarioRepository.deleteById(usuarioId);
            }
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_DELETAR_MEDICO, e);
            throw new UnprocessableEntityException(ERRO_AO_DELETAR_MEDICO);
        }
    }

}
