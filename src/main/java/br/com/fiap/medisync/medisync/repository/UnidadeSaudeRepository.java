package br.com.fiap.medisync.medisync.repository;

import br.com.fiap.medisync.medisync.model.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, UUID> {
}
