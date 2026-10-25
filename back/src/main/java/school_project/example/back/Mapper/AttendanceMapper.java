package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.Attendance.AttendanceRequestDTO;
import school_project.example.back.DTO.school.Attendance.AttendanceResponseDTO;
import school_project.example.back.Entity.Attendance;
import school_project.example.back.Entity.Enrollment;
import school_project.example.back.Entity.TeachingAssignment;

/**
 * Converte a entidade Attendance para DTO e vice-versa,
 * incluindo a associação com matrícula e turma/disciplinas.
 */
public class AttendanceMapper {

    public static AttendanceResponseDTO mapToResponseDTO(
            Attendance attendance) {

        if (attendance == null)
            return null;

        AttendanceResponseDTO dto = new AttendanceResponseDTO();

        dto.setId(attendance.getId());

        dto.setEnrollment(
                EnrollmentMapper.mapToResponseDTO(
                        attendance.getEnrollment()));

        dto.setTeachingAssignment(
                TeachingAssignmentMapper.mapToResponseDTO(
                        attendance.getTeachingAssignment()));

        dto.setDate(attendance.getDate());
        dto.setStatus(attendance.getStatus());

        return dto;
    }

    public static Attendance mapToEntity(
            AttendanceRequestDTO dto,
            Enrollment enrollment,
            TeachingAssignment teachingAssignment) {

        if (dto == null)
            return null;

        Attendance attendance = new Attendance();

        attendance.setEnrollment(enrollment);
        attendance.setTeachingAssignment(teachingAssignment);
        attendance.setDate(dto.getDate());
        attendance.setStatus(dto.getStatus());

        return attendance;
    }
}