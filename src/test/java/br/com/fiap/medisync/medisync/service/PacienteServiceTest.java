package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Paciente;
import br.com.fiap.medisync.medisync.model.Usuario;
import br.com.fiap.medisync.medisync.repository.EnfermeiroRepository;
import br.com.fiap.medisync.medisync.repository.MedicoRepository;
import br.com.fiap.medisync.medisync.repository.PacienteRepository;
import br.com.fiap.medisync.medisync.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("PacienteService Unit Test")
class PacienteServiceTest {

    @Mock private PacienteRepository pacienteRepository;
    @Mock private UsuarioRepository usuarioRepository;
    @Mock private MedicoRepository medicoRepository;
    @Mock private EnfermeiroRepository enfermeiroRepository;

    @InjectMocks private PacienteService pacienteService;

    private static final UUID PACIENTE_ID = UUID.randomUUID();
    private Paciente paciente;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("João da Silva");
        usuario.setEmail("joao@email.com");
        usuario.setLogin("joaosilva");
        usuario.setSenha("SenhaSegura123");
        usuario.setCpf("12345678900");
        usuario.setDataNascimento(LocalDate.of(1991, 10, 31));
        usuario.setTelefone("11999999999");
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setUltimaAlteracao(LocalDateTime.now());

        paciente = new Paciente();
        paciente.setId(PACIENTE_ID);
        paciente.setUsuario(usuario);
        paciente.setCriadoEm(LocalDateTime.now());
        paciente.setUltimaAlteracao(LocalDateTime.now());
    }

    @Test
    void deveListarPacientesComPaginacao() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Paciente> page = new PageImpl<>(List.of(paciente), pageable, 1);

        when(pacienteRepository.findAll(pageable)).thenReturn(page);

        Page<Paciente> resultado = pacienteService.listarPacientes(0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(pacienteRepository).findAll(pageable);
    }

    @Test
    void deveBuscarPacientePorIdExistente() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));

        Paciente resultado = pacienteService.buscarPacientePorId(PACIENTE_ID);

        assertNotNull(resultado);
        assertEquals(PACIENTE_ID, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarPacientePorIdInexistente() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pacienteService.buscarPacientePorId(PACIENTE_ID));
    }

    @Test
    void deveCriarPacienteComUsuarioExistente() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.of(usuario));
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = pacienteService.criarPaciente(paciente);

        assertNotNull(resultado);
        verify(usuarioRepository, never()).save(any());
        verify(pacienteRepository).save(any());
    }

    @Test
    void deveCriarPacienteComNovoUsuario() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(paciente);

        Paciente resultado = pacienteService.criarPaciente(paciente);

        assertNotNull(resultado);
        verify(usuarioRepository).save(any());
        verify(pacienteRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarPacienteComErroDeBanco() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenThrow(new DataAccessException("Erro") {});

        assertThrows(UnprocessableEntityException.class, () -> pacienteService.criarPaciente(paciente));
    }

    @Test
    void deveAtualizarPacienteComSucesso() {
        paciente.getUsuario().setNome("Novo Nome");
        paciente.getUsuario().setEmail("novo@email.com");

        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any())).thenReturn(paciente);

        Paciente resultado = pacienteService.atualizarPaciente(paciente, PACIENTE_ID);

        assertNotNull(resultado);
        verify(pacienteRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarPacienteNaoEncontrado() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> pacienteService.atualizarPaciente(paciente, PACIENTE_ID));
    }

    @Test
    void deveLancarExcecaoAoAtualizarPacienteComErroDeBanco() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));
        when(pacienteRepository.save(any())).thenThrow(new DataAccessException("Erro") {});

        assertThrows(UnprocessableEntityException.class,
                () -> pacienteService.atualizarPaciente(paciente, PACIENTE_ID));
    }

    @Test
    void deveExcluirPacienteComUsuarioNaoVinculado() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));
        when(medicoRepository.existsByUsuarioId(usuario.getId())).thenReturn(false);
        when(enfermeiroRepository.existsByUsuarioId(usuario.getId())).thenReturn(false);

        pacienteService.excluirPacientePorId(PACIENTE_ID);

        verify(pacienteRepository).deleteById(PACIENTE_ID);
        verify(usuarioRepository).deleteById(usuario.getId());
    }

    @Test
    void deveExcluirPacienteComUsuarioVinculado() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));
        when(medicoRepository.existsByUsuarioId(usuario.getId())).thenReturn(true);

        pacienteService.excluirPacientePorId(PACIENTE_ID);

        verify(pacienteRepository).deleteById(PACIENTE_ID);
        verify(usuarioRepository, never()).deleteById(usuario.getId());
    }

    @Test
    void deveLancarExcecaoAoExcluirPacienteNaoEncontrado() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> pacienteService.excluirPacientePorId(PACIENTE_ID));
    }

    @Test
    void deveLancarExcecaoAoExcluirPacienteComErroDeBanco() {
        when(pacienteRepository.findById(PACIENTE_ID)).thenReturn(Optional.of(paciente));
        doThrow(new DataAccessException("Erro") {}).when(pacienteRepository).deleteById(PACIENTE_ID);

        assertThrows(UnprocessableEntityException.class,
                () -> pacienteService.excluirPacientePorId(PACIENTE_ID));
    }
}
