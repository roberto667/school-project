package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.Grade.GradeRequestDTO;
import school_project.example.back.DTO.school.Grade.GradeResponseDTO;

public interface GradeService {

    GradeResponseDTO createGrade(
            GradeRequestDTO dto);

    GradeResponseDTO getGrade(Long gradeId);

    List<GradeResponseDTO> getAllGrades();

    List<GradeResponseDTO> getGradesByEnrollment(
            Long enrollmentId);

    List<GradeResponseDTO> getGradesByTeachingAssignment(
            Long teachingAssignmentId);

    List<GradeResponseDTO> getGradesByEnrollmentAndTeachingAssignment(
            Long enrollmentId,
            Long teachingAssignmentId);

    GradeResponseDTO updateGrade(
            Long gradeId,
            GradeRequestDTO dto);

    void deleteGrade(Long gradeId);
}