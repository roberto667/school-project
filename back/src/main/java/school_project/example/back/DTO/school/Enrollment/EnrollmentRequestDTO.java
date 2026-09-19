package school_project.example.back.DTO.school.Enrollment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para matricular um aluno em uma turma.
public class EnrollmentRequestDTO {

    // Identificador do aluno que será matriculado.
    @NotNull(message = "Aluno é obrigatório")
    private Long studentId;

    // Identificador da turma em que o aluno será matriculado.
    @NotNull(message = "Turma é obrigatória")
    private Long classroomId;
}