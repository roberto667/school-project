package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Teacher;

/**
 * Repositório para acesso aos professores.
 * Permite buscar professores por matrícula e usuário vinculado.
 */
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByMatricula(String matricula);
    List<Teacher> findByUserActiveTrue();

    boolean existsByMatricula(String matricula);

    Optional<Teacher> findByUserId(Long userId);
}