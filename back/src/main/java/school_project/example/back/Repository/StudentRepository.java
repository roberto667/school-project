package school_project.example.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
