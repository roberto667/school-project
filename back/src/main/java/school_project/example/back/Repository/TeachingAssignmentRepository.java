package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.TeachingAssignment;

/**
 * Repositório para acesso às atribuições docentes.
 * Permite consultar vínculos de professor, turma e disciplina.
 */
@Repository
public interface TeachingAssignmentRepository
        extends JpaRepository<TeachingAssignment, Long> {

    List<TeachingAssignment> findByTeacherId(Long teacherId);

    List<TeachingAssignment> findByClassroomId(Long classroomId);

    List<TeachingAssignment> findBySubjectId(Long subjectId);

    List<TeachingAssignment> findByTeacherIdAndClassroomId(
            Long teacherId,
            Long classroomId);

    Optional<TeachingAssignment> findByTeacherIdAndClassroomIdAndSubjectId(
            Long teacherId,
            Long classroomId,
            Long subjectId);

    boolean existsByTeacherIdAndClassroomIdAndSubjectId(
            Long teacherId,
            Long classroomId,
            Long subjectId);
}