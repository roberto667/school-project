package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.Teacher.TeacherRequestDTO;
import school_project.example.back.DTO.users.Teacher.TeacherResponseDTO;

public interface TeacherService {

        TeacherResponseDTO createTeacher(
                        TeacherRequestDTO dto);

        TeacherResponseDTO getTeacher(Long teacherId);

        TeacherResponseDTO getTeacherByMatricula(
                        String matricula);

        List<TeacherResponseDTO> getAllTeachers();

        TeacherResponseDTO updateTeacher(
                        Long teacherId,
                        TeacherRequestDTO dto);

        TeacherResponseDTO updateTeacherByMatricula(
                        String matricula,
                        TeacherRequestDTO dto);

        void activateTeacher(String matricula);

        void deleteTeacher(Long teacherId);

        void deleteTeacherByMatricula(String matricula);
}