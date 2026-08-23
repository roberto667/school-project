package school_project.example.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
