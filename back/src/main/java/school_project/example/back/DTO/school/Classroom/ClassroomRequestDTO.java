package school_project.example.back.DTO.school.Classroom;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para cadastrar ou editar uma turma.
public class ClassroomRequestDTO {

    // Nome ou identificador da turma, como "7A" ou "Turma 2026".
    @NotBlank(message = "Nome da turma é obrigatório")
    private String name;

    // Identificador da série/escolaridade da turma.
    @NotNull(message = "Série/ano escolar é obrigatório")
    private Long schoolGradeId;

    // Ano letivo referente à turma.
    @NotNull(message = "Ano letivo é obrigatório")
    private Integer academicYear;
}