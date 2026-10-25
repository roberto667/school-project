package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.Enrollment.EnrollmentRequestDTO;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.Student;

/**
 * Converte a entidade Enrollment para DTO e vice-versa,
 * relacionando aluno, turma e status da matrícula.
 */
public class EnrollmentMapper {

    public static EnrollmentResponseDTO mapToResponseDTO(
            Enrollment enrollment) {

        if (enrollment == null)
            return null;

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());

        dto.setStudent(
                StudentMapper.mapToResponseDTO(
                        enrollment.getStudent()));

        dto.setClassroom(
                ClassroomMapper.mapToResponseDTO(
                        enrollment.getClassroom()));

        dto.setStatus(enrollment.getStatus());

        return dto;
    }

    public static Enrollment mapToEntity(
            EnrollmentRequestDTO dto,
            Student student,
            Classroom classroom) {

        if (dto == null)
            return null;

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setClassroom(classroom);

        return enrollment;
    }
}