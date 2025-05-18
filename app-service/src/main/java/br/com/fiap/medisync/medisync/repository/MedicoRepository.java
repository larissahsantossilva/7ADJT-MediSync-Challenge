package br.com.fiap.medisync.medisync.repository;

import br.com.fiap.medisync.medisync.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    boolean existsByUsuarioId(UUID id);
}
