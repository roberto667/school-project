package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.Classroom.ClassroomRequestDTO;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;

public interface ClassroomService {

    ClassroomResponseDTO createClassroom(
            ClassroomRequestDTO dto);

    ClassroomResponseDTO getClassroom(Long classroomId);

    List<ClassroomResponseDTO> getAllClassrooms();

    List<ClassroomResponseDTO> getClassroomsByAcademicYear(
            Integer academicYear);

    List<ClassroomResponseDTO> getClassroomsBySchoolGrade(
            Long schoolGradeId);

    List<ClassroomResponseDTO> getClassroomsBySchoolGradeAndAcademicYear(
            Long schoolGradeId,
            Integer academicYear);

    ClassroomResponseDTO updateClassroom(
            Long classroomId,
            ClassroomRequestDTO dto);

    void deleteClassroom(Long classroomId);
}