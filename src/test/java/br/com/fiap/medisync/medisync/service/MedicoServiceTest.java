package br.com.fiap.medisync.medisync.service;

import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.exception.UnprocessableEntityException;
import br.com.fiap.medisync.medisync.model.Especialidade;
import br.com.fiap.medisync.medisync.model.Medico;
import br.com.fiap.medisync.medisync.model.Usuario;
import br.com.fiap.medisync.medisync.repository.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("MedicoService Unit Test")
public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EspecialidadeRepository especialidadeRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;


    @InjectMocks
    private MedicoService medicoService;

    private static final UUID MEDICO_ID = UUID.randomUUID();
    private Medico medico;
    private Usuario usuario;
    private Especialidade especialidade;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome("Ana Maria Souza");
        usuario.setEmail("ana.maria@email.com");
        usuario.setLogin("anamaria");
        usuario.setSenha("Senha@321");
        usuario.setCpf("89792240492");
        usuario.setDataNascimento(LocalDate.of(2002, 9, 22));
        usuario.setTelefone("11999999989");
        usuario.setCriadoEm(LocalDateTime.now());
        usuario.setUltimaAlteracao(LocalDateTime.now());

        especialidade = new Especialidade();
        especialidade.setId(UUID.randomUUID());
        especialidade.setDescricao("Endocrinologia");
        especialidade.setCriadoEm(LocalDateTime.now());
        especialidade.setUltimaAlteracao(LocalDateTime.now());

        medico = new Medico();
        medico.setId(MEDICO_ID);
        medico.setUsuario(usuario);
        medico.setEspecialidade(especialidade);
        medico.setCrm("9722");
        medico.setCriadoEm(LocalDateTime.now());
        medico.setUltimaAlteracao(LocalDateTime.now());
    }

    @Test
    void deveListarMedicosComPaginacao() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Medico> page = new PageImpl<>(List.of(medico), pageable, 1);

        when(medicoRepository.findAll(pageable)).thenReturn(page);

        Page<Medico> resultado = medicoService.listarMedicos(0, 10);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        verify(medicoRepository).findAll(pageable);
    }

    @Test
    void deveBuscarMedicoPorIdExistente() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medico));

        Medico resultado = medicoService.buscarMedicoPorId(MEDICO_ID);

        assertNotNull(resultado);
        assertEquals(MEDICO_ID, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarMedicoPorIdInexistente() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> medicoService.buscarMedicoPorId(MEDICO_ID));
    }

    @Test
    void deveCriarMedicoComUsuarioEEspecializacaoExistentes() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.of(usuario));
        when(especialidadeRepository.findByDescricao(especialidade.getDescricao())).thenReturn(Optional.of(especialidade));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);

        Medico resultado = medicoService.criarMedico(medico);

        assertNotNull(resultado);
        verify(usuarioRepository, never()).save(any());
        verify(especialidadeRepository, never()).save(any());
        verify(medicoRepository).save(any());
    }

    @Test
    void deveCriarMedicoComNovoUsuario() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(especialidadeRepository.findByDescricao(especialidade.getDescricao())).thenReturn(Optional.of(especialidade));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);

        Medico resultado = medicoService.criarMedico(medico);

        assertNotNull(resultado);
        verify(usuarioRepository).save(any());
        verify(especialidadeRepository, never()).save(any());
        verify(medicoRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarMedicoComErroDeBanco() {
        when(usuarioRepository.findByCpf(usuario.getCpf())).thenThrow(new DataAccessException("Erro") {});

        assertThrows(UnprocessableEntityException.class, () -> medicoService.criarMedico(medico));
    }

    @Test
    void deveAtualizarMedicoComSucesso() {
        medico.getUsuario().setNome("Novo Nome");
        medico.getUsuario().setEmail("novo@email.com");
        medico.getEspecialidade().setDescricao("Cardiologia");

        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medico));
        when(medicoRepository.save(any())).thenReturn(medico);

        Medico resultado = medicoService.atualizarMedico(medico, MEDICO_ID);

        assertNotNull(resultado);
        verify(medicoRepository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarMedicoNaoEncontrado() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicoService.atualizarMedico(medico, MEDICO_ID));
    }

    @Test
    void deveLancarExcecaoAoAtualizarMedicoComErroDeBanco() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medico));
        when(medicoRepository.save(any())).thenThrow(new DataAccessException("Erro") {});

        assertThrows(UnprocessableEntityException.class,
                () -> medicoService.atualizarMedico(medico, MEDICO_ID));
    }

    @Test
    void deveExcluirMedicoComUsuarioNaoVinculados() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medico));
        when(pacienteRepository.existsByUsuarioId(usuario.getId())).thenReturn(false);
        when(enfermeiroRepository.existsByUsuarioId(usuario.getId())).thenReturn(false);

        medicoService.excluirMedicoPorId(MEDICO_ID);

        verify(medicoRepository).deleteById(MEDICO_ID);
        verify(usuarioRepository).deleteById(usuario.getId());
    }

    @Test
    void deveLancarExcecaoAoExcluirMedicoNaoEncontrado() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> medicoService.excluirMedicoPorId(MEDICO_ID));
    }

    @Test
    void deveLancarExcecaoAoExcluirMedicoComErroDeBanco() {
        when(medicoRepository.findById(MEDICO_ID)).thenReturn(Optional.of(medico));
        doThrow(new DataAccessException("Erro") {}).when(medicoRepository).deleteById(MEDICO_ID);

        assertThrows(UnprocessableEntityException.class,
                () -> medicoService.excluirMedicoPorId(MEDICO_ID));
    }

}
