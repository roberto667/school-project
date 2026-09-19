package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.DirectorDTO;
import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.User.UserDTO;

public interface UserService {
    StudentRequestDTO createStudent(StudentRequestDTO studentDTO);

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    DirectorDTO createDirector(DirectorDTO directorDTO);

    StudentRequestDTO getStudent(Long studentId);

    TeacherDTO getTeacher(Long teacherId);

    DirectorDTO getDirector(Long directorId);

    StudentRequestDTO getStudentByMatricula(String matricula);

    TeacherDTO getTeacherByMatricula(String matricula);

    DirectorDTO getDirectorByMatricula(String matricula);

    List<StudentRequestDTO> getAllStudents();

    List<TeacherDTO> getAllTeachers();

    List<DirectorDTO> getAllDirectors();

    UserDTO updateUser(Long userId, UserDTO updateUser);

    StudentRequestDTO updateStudent(Long studentId, StudentRequestDTO updateStudent);

    StudentRequestDTO updateStudentByMatricula(String matricula, StudentRequestDTO updateStudent);

    TeacherDTO updateTeacherByMatricula(String matricula, TeacherDTO updateTeacher);

    DirectorDTO updateDirectorByMatricula(String matricula, DirectorDTO updateDirector);

    void deleteUser(Long userId);

    void deleteStudentByMatricula(String matricula);

    void deleteTeacherByMatricula(String matricula);

    void deleteDirectorByMatricula(String matricula);
}