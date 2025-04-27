package br.com.fiap.medisync.medisync.utils;

import br.com.fiap.medisync.medisync.dto.request.PacienteBodyRequest;
import br.com.fiap.medisync.medisync.exception.ResourceNotFoundException;
import br.com.fiap.medisync.medisync.model.Paciente;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static br.com.fiap.medisync.medisync.utils.MediSyncConstants.ID_INVALIDO;
import static java.util.regex.Pattern.matches;

public class MediSyncUtils {
    private static final String REGEX_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public static void uuidValidator(UUID id) {
        if (!matches(REGEX_UUID, id.toString())) {
            throw new ResourceNotFoundException(ID_INVALIDO);
        }
    }

    public static Paciente convertToPaciente(PacienteBodyRequest pacienteRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pacienteRequest, Paciente.class);
    }

}