package school_project.example.back.DTO.school.Attendance;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.AttendanceStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO usado para registrar a presença de um aluno em uma aula.
public class AttendanceRequestDTO {

    // Identificador da matrícula do aluno relacionado à presença.
    @NotNull(message = "Matrícula escolar é obrigatória")
    private Long enrollmentId;

    // Identificador da disciplina/associação do professor em determinada turma.
    @NotNull(message = "Alocação do professor é obrigatória")
    private Long teachingAssignmentId;

    // Data em que a presença foi registrada.
    @NotNull(message = "Data é obrigatória")
    private LocalDate date;

    // Situação da presença: presente, ausente ou justificada.
    @NotNull(message = "Status de presença é obrigatório")
    private AttendanceStatus status;
}