package school_project.example.back.DTO.school.Grade;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para registrar uma nota do aluno em uma disciplina e período
// específicos.
public class GradeRequestDTO {

    // Identificador da matrícula do aluno.
    @NotNull(message = "Matrícula escolar é obrigatória")
    private Long enrollmentId;

    // Identificador da relação professor-turma-disciplina.
    @NotNull(message = "Alocação do professor é obrigatória")
    private Long teachingAssignmentId;

    // Período acadêmico em que a nota foi lançada.
    @NotNull(message = "Período é obrigatório")
    private Long periodId;

    // Nota principal do aluno, válida de 0 a 10.
    @NotNull(message = "Nota é obrigatória")
    @DecimalMin(value = "0.0", message = "Nota não pode ser menor que 0")
    @DecimalMax(value = "10.0", message = "Nota não pode ser maior que 10")
    private Double value;

    // Nota de recuperação, quando houver, também no intervalo de 0 a 10.
    @DecimalMin(value = "0.0", message = "Recuperação não pode ser menor que 0")
    @DecimalMax(value = "10.0", message = "Recuperação não pode ser maior que 10")
    private Double recoveryValue;
}