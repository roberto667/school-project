package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.Student.StudentResponseDTO;
import school_project.example.back.Entity.Student;

/**
 * Converte a entidade Student para DTO e vice-versa,
 * cuidando da matrícula do aluno e dos dados do usuário.
 */
public class StudentMapper {

    public static StudentResponseDTO mapToResponseDTO(Student student) {

        if (student == null)
            return null;

        StudentResponseDTO dto = new StudentResponseDTO();

        dto.setId(student.getId());
        dto.setMatricula(student.getMatricula());
        dto.setUser(
                UserMapper.mapToResponseDTO(student.getUser()));

        return dto;
    }

    public static Student mapToEntity(StudentRequestDTO dto) {

        if (dto == null)
            return null;

        Student student = new Student();

        student.setMatricula(dto.getMatricula());
        student.setUser(
                UserMapper.mapToEntity(dto.getUser()));

        return student;
    }
}