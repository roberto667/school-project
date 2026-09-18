package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Director;
import school_project.example.back.Entity.Student;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Optional<Director> findByMatricula(String matricula);

    Optional<Director> findByUserId(Long userId);
}