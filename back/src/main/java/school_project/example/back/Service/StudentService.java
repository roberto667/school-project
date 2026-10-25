package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.Student.StudentResponseDTO;

public interface StudentService {

        StudentResponseDTO createStudent(
                        StudentRequestDTO dto);

        StudentResponseDTO getStudent(Long studentId);

        StudentResponseDTO getStudentByMatricula(
                        String matricula);

        List<StudentResponseDTO> getAllStudents();

        StudentResponseDTO updateStudent(
                        Long studentId,
                        StudentRequestDTO dto);

        StudentResponseDTO updateStudentByMatricula(
                        String matricula,
                        StudentRequestDTO dto);

        void activateStudent(String matricula);

        void deleteStudent(Long studentId);

        void deleteStudentByMatricula(String matricula);
}