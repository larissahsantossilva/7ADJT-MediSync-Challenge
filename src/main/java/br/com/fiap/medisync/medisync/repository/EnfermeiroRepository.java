package br.com.fiap.medisync.medisync.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.medisync.medisync.model.Enfermeiro;

@Repository
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, UUID> {
}
