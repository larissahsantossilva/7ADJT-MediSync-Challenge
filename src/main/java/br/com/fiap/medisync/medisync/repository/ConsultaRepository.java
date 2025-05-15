package br.com.fiap.medisync.medisync.repository;

import br.com.fiap.medisync.medisync.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {
}
