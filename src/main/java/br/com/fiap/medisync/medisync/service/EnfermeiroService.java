package br.com.fiap.medisync.medisync.service;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.*;
import static br.com.fiap.medisync.medisync.utils.MediSyncUtils.uuidValidator;
import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Enfermeiro;
import br.com.fiap.medisync.medisync.model.Especialidade;
import br.com.fiap.medisync.medisync.model.Usuario;
import br.com.fiap.medisync.medisync.repository.EnfermeiroRepository;
import br.com.fiap.medisync.medisync.repository.EspecialidadeRepository;
import br.com.fiap.medisync.medisync.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnfermeiroService {

    private static final Logger logger = getLogger(EnfermeiroService.class);
    
    private final EnfermeiroRepository enfermeiroRepository;  
    private final EspecialidadeRepository especialidadeRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<Enfermeiro> listarEnfermeiros(int page, int size) {
        return enfermeiroRepository.findAll(PageRequest.of(page, size));
    }

    public Enfermeiro buscarEnfermeiroPorId(UUID id) {
        uuidValidator(id);
        return enfermeiroRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ENFERMEIRO_NAO_ENCONTRADO));
    }

    public Enfermeiro criarEnfermeiro (Enfermeiro enfermeiro) {
        try {
            Especialidade especialidadePersistida = especialidadeRepository
                    .findByDescricao(enfermeiro.getEspecialidade().getDescricao())
                    .orElseGet(() -> especialidadeRepository.save(enfermeiro.getEspecialidade()));

            enfermeiro.setEspecialidade(especialidadePersistida);

            Usuario usuarioPersistido = usuarioRepository
                    .findByCpf(enfermeiro.getUsuario().getCpf())
                    .orElseGet(() -> usuarioRepository.save(enfermeiro.getUsuario()));

            enfermeiro.setUsuario(usuarioPersistido);

            return enfermeiroRepository.save(enfermeiro);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_ENFERMEIRO, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_ENFERMEIRO);
        }
    }

    public Enfermeiro atualizarEnfermeiro(Enfermeiro enfermeiro, UUID id) {
    	Enfermeiro enfermeiroExistente = buscarEnfermeiroPorId(id);
        atualizarUsuarioEnfermeiro(enfermeiro, enfermeiroExistente);
        atualizarEspecialidadeEnfermeiro(enfermeiro, enfermeiroExistente);
        if (enfermeiroExistente.getCoren() != null) enfermeiroExistente.setCoren(enfermeiro.getCoren());
        enfermeiroExistente.setUltimaAlteracao(now());
        return saveEnfermeiro(enfermeiroExistente);
    }

	private void atualizarUsuarioEnfermeiro(Enfermeiro enfermeiro, Enfermeiro enfermeiroExistente) {
		if (enfermeiro.getUsuario() != null) {
            Usuario usuarioExistente = enfermeiroExistente.getUsuario();
            Usuario usuarioAtualizado = enfermeiro.getUsuario();
            if (usuarioAtualizado.getNome() != null) usuarioExistente.setNome(usuarioAtualizado.getNome());
            if (usuarioAtualizado.getEmail() != null) usuarioExistente.setEmail(usuarioAtualizado.getEmail());
            if (usuarioAtualizado.getLogin() != null) usuarioExistente.setLogin(usuarioAtualizado.getLogin());
            if (usuarioAtualizado.getSenha() != null) usuarioExistente.setSenha(usuarioAtualizado.getSenha());
            if (usuarioAtualizado.getCpf() != null) usuarioExistente.setCpf(usuarioAtualizado.getCpf());
            if (usuarioAtualizado.getTelefone() != null) usuarioExistente.setTelefone(usuarioAtualizado.getTelefone());
            if (usuarioAtualizado.getDataNascimento() != null) usuarioExistente.setDataNascimento(usuarioAtualizado.getDataNascimento());
            if (usuarioAtualizado.getUltimaAlteracao() != null) usuarioExistente.setUltimaAlteracao(now());
        }
	}
	
	private void atualizarEspecialidadeEnfermeiro(Enfermeiro enfermeiro, Enfermeiro enfermeiroExistente) {
		if (enfermeiro.getEspecialidade() != null) {
			Especialidade especialidadeExistente = enfermeiroExistente.getEspecialidade();
			Especialidade especialidadeAtualizada = enfermeiro.getEspecialidade();
			if (especialidadeAtualizada.getDescricao() != null) especialidadeExistente.setDescricao(especialidadeAtualizada.getDescricao());
			if (especialidadeAtualizada.getUltimaAlteracao() != null) especialidadeExistente.setUltimaAlteracao(now());
		}
	}
	
	private Enfermeiro saveEnfermeiro(Enfermeiro enfermeiroExistente) {
		try {
            return enfermeiroRepository.save(enfermeiroExistente);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_ALTERAR_ENFERMEIRO, e);
            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_ENFERMEIRO);
        }
	}
	
    public void excluirEnfermeiroPorId(UUID id) {
        try {
            enfermeiroRepository.deleteById(id);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_DELETAR_ENFERMEIRO, e);
            throw new UnprocessableEntityException(ERRO_AO_DELETAR_ENFERMEIRO);
        }
    }

}
