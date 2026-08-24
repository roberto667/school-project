package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.DirectorDTO;
import school_project.example.back.DTO.users.StudentDTO;
import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.DTO.users.UserDTO;

public interface UserService {
    StudentDTO createStudent(StudentDTO studentDTO);

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    DirectorDTO createDirector(DirectorDTO directorDTO);

    StudentDTO getStudent(Long studentId);

    TeacherDTO getTeacher(Long teacherId);

    DirectorDTO getDirector(Long directorId);

    List<StudentDTO> getAllStudents();

    List<TeacherDTO> getAllTeachers();

    List<DirectorDTO> getAllDirectors();

    UserDTO updateUser(Long userId, UserDTO updateUser);

    void deleteUser(Long userId);

}
