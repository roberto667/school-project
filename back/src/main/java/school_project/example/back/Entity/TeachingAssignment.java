package school_project.example.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teaching_assignments")
public class TeachingAssignment {

    // Identificador único do vínculo de ensino.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Professor responsável por ministrar a disciplina.
    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // Turma em que a disciplina será dada.
    @ManyToOne(optional = false)
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    // Disciplina que será ensinada nessa turma.
    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
}