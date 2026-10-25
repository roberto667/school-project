package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.Grade.GradeRequestDTO;
import school_project.example.back.DTO.school.Grade.GradeResponseDTO;
import school_project.example.back.Entity.AcademicPeriod;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.Grade;
import school_project.example.back.Entity.TeachingAssignment;

/**
 * Converte a entidade Grade para DTO e vice-versa,
 * incluindo notas, recuperação e os relacionamentos com matrícula e disciplina.
 */
public class GradeMapper {

    public static GradeResponseDTO mapToResponseDTO(
            Grade grade) {

        if (grade == null)
            return null;

        GradeResponseDTO dto = new GradeResponseDTO();

        dto.setId(grade.getId());

        dto.setEnrollment(
                EnrollmentMapper.mapToResponseDTO(
                        grade.getEnrollment()));

        dto.setTeachingAssignment(
                TeachingAssignmentMapper.mapToResponseDTO(
                        grade.getTeachingAssignment()));

        dto.setPeriod(
                AcademicPeriodMapper.mapToResponseDTO(
                        grade.getPeriod()));

        dto.setValue(grade.getValue());
        dto.setRecoveryValue(grade.getRecoveryValue());

        return dto;
    }

    public static Grade mapToEntity(
            GradeRequestDTO dto,
            Enrollment enrollment,
            TeachingAssignment teachingAssignment,
            AcademicPeriod period) {

        if (dto == null)
            return null;

        Grade grade = new Grade();

        grade.setEnrollment(enrollment);
        grade.setTeachingAssignment(teachingAssignment);
        grade.setPeriod(period);
        grade.setValue(dto.getValue());
        grade.setRecoveryValue(dto.getRecoveryValue());

        return grade;
    }
}