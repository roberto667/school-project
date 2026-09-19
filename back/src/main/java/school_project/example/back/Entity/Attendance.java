package school_project.example.back.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.AttendanceStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attendance")
public class Attendance {

    // Identificador único do registro de presença.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relação com a matrícula do aluno na turma.
    @ManyToOne(optional = false)
    private Enrollment enrollment;

    // Relação com a associação de professor, disciplina e turma dessa aula.
    @ManyToOne(optional = false)
    private TeachingAssignment teachingAssignment;

    // Data em que a presença foi registrada.
    @Column(nullable = false)
    private LocalDate date;

    // Status da presença: presente, ausente ou justificada.
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
}