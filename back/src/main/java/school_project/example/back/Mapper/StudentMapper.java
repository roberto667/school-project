package school_project.example.back.Mapper;

import school_project.example.back.DTO.StudentDTO;
import school_project.example.back.Entity.Student;

public class StudentMapper {
    public static StudentDTO maptoStudentDTO(Student student) {
        if (student == null)
            return null;
        StudentDTO dto = new StudentDTO();
        dto.setUser(UserMapper.maptoUserDTO(student.getUser()));
        dto.setMatricula(student.getMatricula());
        dto.setId(student.getId());
        return dto;
    }

    public static Student maptoStudent(StudentDTO studentDTO) {
        if (studentDTO == null)
            return null;
        Student student = new Student();
        student.setMatricula(studentDTO.getMatricula());
        student.setUser(UserMapper.maptoUser(studentDTO.getUser()));
        student.setId(studentDTO.getId());
        return student;

    }

}
