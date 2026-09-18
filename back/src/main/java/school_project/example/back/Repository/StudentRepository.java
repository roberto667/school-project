package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByMatricula(String matricula);

    Optional<Student> findByUserId(Long userId);
}
