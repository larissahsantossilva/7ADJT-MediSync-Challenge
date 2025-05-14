package br.com.fiap.medisync.medisync.repository;

import br.com.fiap.medisync.medisync.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    boolean existsByUsuarioId(UUID id);
}
