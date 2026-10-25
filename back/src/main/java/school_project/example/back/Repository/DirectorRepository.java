package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Director;

/**
 * Repositório para acesso aos diretores.
 * Permite buscar diretores por matrícula e usuário vinculado.
 */
public interface DirectorRepository extends JpaRepository<Director, Long> {

    Optional<Director> findByMatricula(String matricula);
    List<Director> findByUserActiveTrue();

    boolean existsByMatricula(String matricula);

    Optional<Director> findByUserId(Long userId);
}