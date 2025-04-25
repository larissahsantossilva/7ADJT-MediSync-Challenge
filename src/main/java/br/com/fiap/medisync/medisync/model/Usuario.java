package br.com.fiap.medisync.medisync.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario", schema = "medisync")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate data_nascimento;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "criado_em", nullable = false)
    private LocalDate criado_em = LocalDate.now();

    @Column(name = "ultima_alteracao", nullable = false)
    private LocalDate ultima_alteracao = LocalDate.now();

}
