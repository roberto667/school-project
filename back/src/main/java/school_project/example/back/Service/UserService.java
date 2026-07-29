package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.StudentDTO;
import school_project.example.back.DTO.TeacherDTO;
import school_project.example.back.DTO.UserDTO;

public interface UserService {
    UserService createStudent(StudentDTO studentDTO);

    UserService createTeacher(TeacherDTO teacherDTO);

    UserService getStudent(Long userid);

    UserService getTecaher(Long userid);

    List<StudentDTO> getAllStudents();

    List<TeacherDTO> getAllTecahers();

    UserDTO updateUser(Long userId, UserDTO updateUser);

    void deleteUser(Long userId);

}
