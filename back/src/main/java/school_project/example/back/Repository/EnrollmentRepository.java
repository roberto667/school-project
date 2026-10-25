package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.enums.EnrollmentStatus;

/**
 * Repositório para acesso às matrículas dos alunos.
 * Permite consultar inscrições por aluno, turma e status.
 */
@Repository
public interface EnrollmentRepository
        extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);

    List<Enrollment> findByClassroomId(Long classroomId);

    List<Enrollment> findByStudentIdAndStatus(
            Long studentId,
            EnrollmentStatus status);

    Optional<Enrollment> findByStudentIdAndClassroomId(
            Long studentId,
            Long classroomId);

    boolean existsByStudentIdAndClassroomId(
            Long studentId,
            Long classroomId);

    boolean existsByStudentIdAndStatus(
            Long studentId,
            EnrollmentStatus status);
}