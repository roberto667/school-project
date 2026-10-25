package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.Student;

/**
 * Repositório para acesso aos alunos.
 * Permite buscar estudantes por matrícula e usuário vinculado.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByMatricula(String matricula);
    List<Student> findByUserActiveTrue();

    boolean existsByMatricula(String matricula);

    Optional<Student> findByUserId(Long userId);
}