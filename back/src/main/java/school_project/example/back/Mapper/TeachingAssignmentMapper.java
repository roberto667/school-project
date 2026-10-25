package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentRequestDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;
import school_project.example.back.Entity.Classroom;
import school_project.example.back.Entity.Subject;
import school_project.example.back.Entity.Teacher;
import school_project.example.back.Entity.TeachingAssignment;

/**
 * Converte a entidade TeachingAssignment para DTO e vice-versa,
 * relacionando professor, turma e disciplina da atribuição docente.
 */
public class TeachingAssignmentMapper {

    public static TeachingAssignmentResponseDTO mapToResponseDTO(
            TeachingAssignment assignment) {

        if (assignment == null)
            return null;

        TeachingAssignmentResponseDTO dto = new TeachingAssignmentResponseDTO();

        dto.setId(assignment.getId());

        dto.setTeacher(
                TeacherMapper.mapToResponseDTO(
                        assignment.getTeacher()));

        dto.setClassroom(
                ClassroomMapper.mapToResponseDTO(
                        assignment.getClassroom()));

        dto.setSubject(
                SubjectMapper.mapToResponseDTO(
                        assignment.getSubject()));

        return dto;
    }

    public static TeachingAssignment mapToEntity(
            TeachingAssignmentRequestDTO dto,
            Teacher teacher,
            Classroom classroom,
            Subject subject) {

        if (dto == null)
            return null;

        TeachingAssignment assignment = new TeachingAssignment();

        assignment.setTeacher(teacher);
        assignment.setClassroom(classroom);
        assignment.setSubject(subject);

        return assignment;
    }
}