package school_project.example.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school_project.example.back.Entity.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByStudentId(Long studentId);
}