package school_project.example.back.DTO.school.Enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;
import school_project.example.back.DTO.users.Student.StudentResponseDTO;
import school_project.example.back.Entity.enums.EnrollmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com a matrícula completa do aluno.
public class EnrollmentResponseDTO {

    // Identificador único da matrícula.
    private Long id;

    // Dados do aluno matriculado.
    private StudentResponseDTO student;

    // Dados da turma em que o aluno está matriculado.
    private ClassroomResponseDTO classroom;

    // Situação atual da matrícula.
    private EnrollmentStatus status;
}