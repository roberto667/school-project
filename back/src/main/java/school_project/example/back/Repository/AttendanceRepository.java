package school_project.example.back.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.Attendance;
import school_project.example.back.Entity.enums.AttendanceStatus;

/**
 * Repositório para acesso às frequências dos alunos.
 * Permite consultar presenças por matrícula, disciplina e status.
 */
@Repository
public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByEnrollmentId(
            Long enrollmentId);

    List<Attendance> findByTeachingAssignmentId(
            Long teachingAssignmentId);

    List<Attendance> findByEnrollmentIdAndTeachingAssignmentId(
            Long enrollmentId,
            Long teachingAssignmentId);

    List<Attendance> findByEnrollmentIdAndStatus(
            Long enrollmentId,
            AttendanceStatus status);

    List<Attendance> findByEnrollmentIdAndTeachingAssignmentIdAndStatus(
            Long enrollmentId,
            Long teachingAssignmentId,
            AttendanceStatus status);

    Optional<Attendance> findByEnrollmentIdAndTeachingAssignmentIdAndDate(
            Long enrollmentId,
            Long teachingAssignmentId,
            LocalDate date);

    boolean existsByEnrollmentIdAndTeachingAssignmentIdAndDate(
            Long enrollmentId,
            Long teachingAssignmentId,
            LocalDate date);

    long countByEnrollmentIdAndStatus(
            Long enrollmentId,
            AttendanceStatus status);

    long countByEnrollmentIdAndTeachingAssignmentIdAndStatus(
            Long enrollmentId,
            Long teachingAssignmentId,
            AttendanceStatus status);
}