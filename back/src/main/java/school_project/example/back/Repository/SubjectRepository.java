package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Subject;

/**
 * Repositório para acesso às disciplinas.
 * Permite consultar e validar disciplinas por nome.
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}