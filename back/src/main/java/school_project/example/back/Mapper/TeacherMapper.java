package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.Entity.Teacher;

public class TeacherMapper {
    public static TeacherDTO maptoTeacherDTO(Teacher teacher) {
        if (teacher == null)
            return null;
        TeacherDTO dto = new TeacherDTO();

        dto.setId(teacher.getId());
        dto.setUser(UserMapper.maptoUserDTO(teacher.getUser()));
        dto.setMatricula(teacher.getMatricula());

        return dto;
    }

    public static Teacher maptoTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO == null)
            return null;
        Teacher teacher = new Teacher();

        teacher.setId(teacherDTO.getId());
        teacher.setMatricula(teacherDTO.getMatricula());
        teacher.setUser(UserMapper.maptoUser(teacherDTO.getUser()));

        return teacher;

    }

}
