package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.StudentDTO;
import school_project.example.back.DTO.TeacherDTO;
import school_project.example.back.DTO.UserDTO;

public interface UserService {
    StudentDTO createStudent(StudentDTO studentDTO);

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

    StudentDTO getStudent(Long studentId);

    TeacherDTO getTeacher(Long teacherId);

    List<StudentDTO> getAllStudents();

    List<TeacherDTO> getAllTeachers();

    UserDTO updateUser(Long userId, UserDTO updateUser);

    void deleteUser(Long userId);

}
