package school_project.example.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    // Identificador único do usuário no sistema.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Primeiro nome da pessoa.
    @Column(name = "first_name")
    private String firstName;

    // Sobrenome da pessoa.
    @Column(name = "last_name")
    private String lastName;

    // Endereço de e-mail usado para login e identificação do usuário.
    @Column(name = "email_id", nullable = false, unique = true)
    private String email;

    // Senha do usuário, geralmente armazenada de forma segura.
    @Column(name = "password", nullable = false)
    private String password;

    // Perfil do aluno associado a este usuário.
    @OneToOne(mappedBy = "user")
    private Student student;

    // Perfil do professor associado a este usuário.
    @OneToOne(mappedBy = "user")
    private Teacher teacher;

    // Perfil do diretor associado a este usuário.
    @OneToOne(mappedBy = "user")
    private Director director;
}
