package school_project.example.back.DTO.school.Grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodResponseDTO;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com a nota lançada para o aluno.
public class GradeResponseDTO {

    // Identificador único da nota.
    private Long id;

    // Matrícula e aluno relacionado à nota.
    private EnrollmentResponseDTO enrollment;

    // Professor, disciplina e turma que originaram a nota.
    private TeachingAssignmentResponseDTO teachingAssignment;

    // Período acadêmico em que a nota foi registrada.
    private AcademicPeriodResponseDTO period;

    // Valor da nota principal do aluno.
    private Double value;

    // Valor da nota de recuperação, se existir.
    private Double recoveryValue;
}