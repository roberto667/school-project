package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Student;
import school_project.example.back.Entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByMatricula(String matricula);

    Optional<Teacher> findByUserId(Long userId);

}
