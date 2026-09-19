package school_project.example.back.DTO.school.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.school.Enrollment.EnrollmentResponseDTO;
import school_project.example.back.DTO.school.TeachingAssignment.TeachingAssignmentResponseDTO;
import school_project.example.back.Entity.enums.AttendanceStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com o registro de presença completo.
public class AttendanceResponseDTO {

    // Identificador único da presença.
    private Long id;

    // Dados da matrícula do aluno associada ao registro.
    private EnrollmentResponseDTO enrollment;

    // Dados da aula/professor/disciplina vinculada à presença.
    private TeachingAssignmentResponseDTO teachingAssignment;

    // Data da chamada.
    private LocalDate date;

    // Status da presença do aluno.
    private AttendanceStatus status;
}