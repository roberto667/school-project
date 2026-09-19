package school_project.example.back.DTO.school.TeachingAssignment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para vincular professor, turma e disciplina em uma atribuição de
// ensino.
public class TeachingAssignmentRequestDTO {

    // Identificador do professor responsável pela disciplina.
    @NotNull(message = "Professor é obrigatório")
    private Long teacherId;

    // Identificador da turma em que a disciplina será ministrada.
    @NotNull(message = "Turma é obrigatória")
    private Long classroomId;

    // Identificador da disciplina que será lecionada.
    @NotNull(message = "Disciplina é obrigatória")
    private Long subjectId;
}