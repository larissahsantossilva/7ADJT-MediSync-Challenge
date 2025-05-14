package br.com.fiap.medisync.medisync.repository;

import br.com.fiap.medisync.medisync.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, UUID> {

    Optional<Especialidade> findByDescricao(String descricao);

}
