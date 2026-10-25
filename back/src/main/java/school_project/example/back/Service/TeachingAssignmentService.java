package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentRequestDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;

public interface TeachingAssignmentService {

    TeachingAssignmentResponseDTO createAssignment(
            TeachingAssignmentRequestDTO dto);

    TeachingAssignmentResponseDTO getAssignment(Long id);

    List<TeachingAssignmentResponseDTO> getAllAssignments();

    List<TeachingAssignmentResponseDTO> getAssignmentsByTeacher(
            Long teacherId);

    List<TeachingAssignmentResponseDTO> getAssignmentsByClassroom(
            Long classroomId);

    List<TeachingAssignmentResponseDTO> getAssignmentsBySubject(
            Long subjectId);

    void deleteAssignment(Long id);
}