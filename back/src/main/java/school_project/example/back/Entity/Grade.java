package school_project.example.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.EnrollmentStatus;
import school_project.example.back.Entity.enums.Grau;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "grades")
public class Grade {

    // Identificador único da nota registrada.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Matrícula do aluno que recebeu essa nota.
    @ManyToOne(optional = false)
    private Enrollment enrollment;

    // Vínculo entre professor, disciplina e turma ao qual a nota pertence.
    @ManyToOne(optional = false)
    private TeachingAssignment teachingAssignment;

    // Período acadêmico em que a nota foi lançada.
    @ManyToOne(optional = false)
    private AcademicPeriod period;

    // Valor da nota principal do aluno.
    private Double value;

    // Valor da nota de recuperação, quando houver.
    private Double recoveryValue;
}