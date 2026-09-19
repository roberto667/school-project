package school_project.example.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.EnrollmentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment {

    // Identificador único da matrícula do aluno.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aluno que foi matriculado na turma.
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Turma em que o aluno está matriculado.
    @ManyToOne(optional = false)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    // Situação atual da matrícula: ativa, aprovada, reprovada, transferida ou
    // cancelada.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

}
