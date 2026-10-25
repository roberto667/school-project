package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.Enrollment.EnrollmentRequestDTO;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.Entity.enums.EnrollmentStatus;

public interface EnrollmentService {

    EnrollmentResponseDTO createEnrollment(
            EnrollmentRequestDTO dto);

    EnrollmentResponseDTO getEnrollment(Long enrollmentId);

    List<EnrollmentResponseDTO> getAllEnrollments();

    List<EnrollmentResponseDTO> getEnrollmentsByStudent(
            Long studentId);

    List<EnrollmentResponseDTO> getEnrollmentsByClassroom(
            Long classroomId);

    List<EnrollmentResponseDTO> getEnrollmentsByStudentAndStatus(
            Long studentId,
            EnrollmentStatus status);

    EnrollmentResponseDTO updateStatus(
            Long enrollmentId,
            EnrollmentStatus status);

    void deleteEnrollment(Long enrollmentId);
}