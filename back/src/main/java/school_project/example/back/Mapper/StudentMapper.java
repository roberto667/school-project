package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.Entity.Student;

public class StudentMapper {
    public static StudentRequestDTO maptoStudentDTO(Student student) {
        if (student == null)
            return null;
        StudentRequestDTO dto = new StudentRequestDTO();
        dto.setUser(UserMapper.maptoUserDTO(student.getUser()));
        dto.setMatricula(student.getMatricula());
        return dto;
    }

    public static Student maptoStudent(StudentRequestDTO dto) {
        if (dto == null)
            return null;
        Student student = new Student();
        student.setUser(UserMapper.maptoUser(dto.getUser()));
        student.setMatricula(dto.getMatricula());
        return student;
    }

}
