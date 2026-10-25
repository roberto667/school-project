package school_project.example.back.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Grade;

/**
 * Repositório para acesso às notas dos alunos.
 * Permite buscar avaliações por matrícula, disciplina e período letivo.
 */
public interface GradeRepository
        extends JpaRepository<Grade, Long> {

    List<Grade> findByEnrollmentId(Long enrollmentId);

    List<Grade> findByTeachingAssignmentId(
            Long teachingAssignmentId);

    List<Grade> findByPeriodId(Long periodId);

    List<Grade> findByEnrollmentIdAndTeachingAssignmentId(
            Long enrollmentId,
            Long teachingAssignmentId);

    Optional<Grade> findByEnrollmentIdAndTeachingAssignmentIdAndPeriodId(
            Long enrollmentId,
            Long teachingAssignmentId,
            Long periodId);

    boolean existsByEnrollmentIdAndTeachingAssignmentIdAndPeriodId(
            Long enrollmentId,
            Long teachingAssignmentId,
            Long periodId);
}