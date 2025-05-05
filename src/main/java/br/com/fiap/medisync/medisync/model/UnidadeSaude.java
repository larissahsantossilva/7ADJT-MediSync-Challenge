package br.com.fiap.medisync.medisync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "unidade_saude", schema = "medisync")
public class UnidadeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "criadoEm", nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column(name = "ultimaAlteracao", nullable = false)
    private LocalDateTime ultimaAlteracao = LocalDateTime.now();

}
