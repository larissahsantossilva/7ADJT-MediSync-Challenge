package br.com.fiap.medisync.medisync.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Enfermeiro;
import br.com.fiap.medisync.medisync.model.Especialidade;
import br.com.fiap.medisync.medisync.model.Usuario;
import br.com.fiap.medisync.medisync.repository.EnfermeiroRepository;
import br.com.fiap.medisync.medisync.repository.EspecialidadeRepository;
import br.com.fiap.medisync.medisync.repository.MedicoRepository;
import br.com.fiap.medisync.medisync.repository.PacienteRepository;
import br.com.fiap.medisync.medisync.repository.UsuarioRepository;

@DisplayName("EnfermeiroService Unit Test")
class EnfermeiroServiceTest {
	
	@Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @InjectMocks
    private EnfermeiroService enfermeiroService;

    private Enfermeiro enfermeiro;
    private Usuario usuario;
    private Especialidade especialidade;

    private final UUID ENFERMEIRO_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Carlos Silva");
        usuario.setEmail("carlos@email.com");
        usuario.setLogin("carlos123");
        usuario.setSenha("Senha@321");
        usuario.setCpf("12345678901");
        usuario.setDataNascimento(LocalDate.of(1990, 1, 1));
        usuario.setTelefone("11999999999");
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setUltimaAlteracao(LocalDateTime.now());

        especialidade = new Especialidade();
        especialidade.setId(UUID.randomUUID());
        especialidade.setDescricao("Pediatria");
        especialidade.setCriadoEm(LocalDateTime.now());
        especialidade.setUltimaAlteracao(LocalDateTime.now());

        enfermeiro = new Enfermeiro();
        enfermeiro.setId(ENFERMEIRO_ID);
        enfermeiro.setUsuario(usuario);
        enfermeiro.setEspecialidade(especialidade);
        enfermeiro.setCoren("COREN123");
        enfermeiro.setCriadoEm(LocalDateTime.now());
        enfermeiro.setUltimaAlteracao(LocalDateTime.now());
    }

    @Test
    void deveListarEnfermeirosComPaginacao() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Enfermeiro> page = new PageImpl<>(List.of(enfermeiro), pageable, 1);

        when(enfermeiroRepository.findAll(pageable)).thenReturn(page);

        Page<Enfermeiro> resultado = enfermeiroService.listarEnfermeiros(0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(enfermeiroRepository).findAll(pageable);
    }

    @Test
    void deveBuscarEnfermeiroPorIdExistente() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));

        Enfermeiro resultado = enfermeiroService.buscarEnfermeiroPorId(ENFERMEIRO_ID);

        assertNotNull(resultado);
        assertEquals(ENFERMEIRO_ID, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarEnfermeiroPorIdInexistente() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enfermeiroService.buscarEnfermeiroPorId(ENFERMEIRO_ID));
    }

    @Test
    void deveCriarEnfermeiroComUsuarioEEspecialidadeExistentes() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.of(usuario));
        when(especialidadeRepository.findByDescricao(especialidade.getDescricao())).thenReturn(Optional.of(especialidade));
        when(enfermeiroRepository.save(any())).thenReturn(enfermeiro);

        Enfermeiro resultado = enfermeiroService.criarEnfermeiro(enfermeiro);

        assertNotNull(resultado);
        verify(usuarioRepository, never()).save(any());
        verify(enfermeiroRepository).save(any());
    }

    @Test
    void deveCriarEnfermeiroComNovoUsuario() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(especialidadeRepository.findByDescricao(especialidade.getDescricao())).thenReturn(Optional.of(especialidade));
        when(enfermeiroRepository.save(any())).thenReturn(enfermeiro);

        Enfermeiro resultado = enfermeiroService.criarEnfermeiro(enfermeiro);

        assertNotNull(resultado);
        verify(usuarioRepository).save(any());
        verify(enfermeiroRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarEnfermeiroComErroDeBanco() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenThrow(new DataAccessException("Erro") {
			private static final long serialVersionUID = 1L;});

        assertThrows(UnprocessableEntityException.class, () -> enfermeiroService.criarEnfermeiro(enfermeiro));
    }

    @Test
    void deveAtualizarEnfermeiroComSucesso() {
        enfermeiro.getUsuario().setNome("Novo Nome");
        enfermeiro.getEspecialidade().setDescricao("Cardiologia");

        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));
        when(enfermeiroRepository.save(any())).thenReturn(enfermeiro);

        Enfermeiro resultado = enfermeiroService.atualizarEnfermeiro(enfermeiro, ENFERMEIRO_ID);

        assertNotNull(resultado);
        verify(enfermeiroRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarEnfermeiroNaoEncontrado() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enfermeiroService.atualizarEnfermeiro(enfermeiro, ENFERMEIRO_ID));
    }

    @Test
    void deveLancarExcecaoAoAtualizarEnfermeiroComErroDeBanco() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));
        when(enfermeiroRepository.save(any())).thenThrow(new DataAccessException("Erro") {
			private static final long serialVersionUID = 1L;});

        assertThrows(UnprocessableEntityException.class, () -> enfermeiroService.atualizarEnfermeiro(enfermeiro, ENFERMEIRO_ID));
    }

    @Test
    void deveExcluirEnfermeiroComSucesso() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));

        enfermeiroService.excluirEnfermeiroPorId(ENFERMEIRO_ID);

        verify(enfermeiroRepository).deleteById(ENFERMEIRO_ID);
        verify(usuarioRepository).deleteById(usuario.getId());
    }
    
    @Test
    void deveExcluirEnfermeiroComUsuarioNaoVinculados() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));
        when(pacienteRepository.existsByUsuarioId(usuario.getId())).thenReturn(false);

        enfermeiroService.excluirEnfermeiroPorId(ENFERMEIRO_ID);

        verify(enfermeiroRepository).deleteById(ENFERMEIRO_ID);
        verify(usuarioRepository).deleteById(usuario.getId());
    }
    
    @Test
    void deveLancarExcecaoAoExcluirEnfermeiroNaoEncontrado() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> enfermeiroService.excluirEnfermeiroPorId(ENFERMEIRO_ID));
    }

    @Test
    void deveLancarExcecaoAoExcluirEnfermeiroComErroDeBanco() {
        when(enfermeiroRepository.findById(ENFERMEIRO_ID)).thenReturn(Optional.of(enfermeiro));
        doThrow(new DataAccessException("Erro") {
			private static final long serialVersionUID = 1L;}).when(enfermeiroRepository).deleteById(ENFERMEIRO_ID);

        assertThrows(UnprocessableEntityException.class, () -> enfermeiroService.excluirEnfermeiroPorId(ENFERMEIRO_ID));
    }
}
