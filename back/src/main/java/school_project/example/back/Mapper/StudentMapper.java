package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.StudentDTO;
import school_project.example.back.Entity.Student;

public class StudentMapper {
    public static StudentDTO maptoStudentDTO(Student student) {
        if (student == null)
            return null;
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setUser(UserMapper.maptoUserDTO(student.getUser()));
        dto.setMatricula(student.getMatricula());
        dto.setGrau(student.getGrau());
        dto.setAno(student.getAno());
        return dto;
    }

    public static Student maptoStudent(StudentDTO dto) {
        if (dto == null)
            return null;
        Student student = new Student();
        student.setId(dto.getId());
        student.setUser(UserMapper.maptoUser(dto.getUser()));
        student.setMatricula(dto.getMatricula());
        student.setGrau(dto.getGrau());
        student.setAno(dto.getAno());
        return student;
    }

}
