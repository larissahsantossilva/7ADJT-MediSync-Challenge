package br.com.fiap.medisync.notificacao.messaging;

import br.com.fiap.medisync.notificacao.dto.ConsultaStatusMessageDTO;
import br.com.fiap.medisync.notificacao.model.Notificacao;
import br.com.fiap.medisync.notificacao.repository.NotificacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

class NotificacaoListenerTest {

    @InjectMocks
    private NotificacaoListener listener;

    @Mock
    private NotificacaoRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarNotificacaoAoReceberMensagemValida() {
        // Arrange
        ConsultaStatusMessageDTO dto = new ConsultaStatusMessageDTO();
        dto.setIdConsulta(UUID.randomUUID());
        dto.setIdPaciente(UUID.randomUUID());
        dto.setMensagem("Consulta agendada para amanhã.");
        dto.setDataConsulta(LocalDate.now().plusDays(1));

        // Act
        listener.receberMensagem(dto);

        // Assert
        verify(repository, times(1)).save(argThat(n ->
                n.getIdConsulta().equals(dto.getIdConsulta()) &&
                        n.getIdPaciente().equals(dto.getIdPaciente()) &&
                        n.getMensagem().equals(dto.getMensagem()) &&
                        n.getDataEnvio().equals(dto.getDataConsulta())
        ));
    }
}
