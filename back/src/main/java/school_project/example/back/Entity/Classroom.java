package school_project.example.back.Entity;

import java.util.List;

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
@Table(name = "classrooms")
public class Classroom {

    // Identificador único da turma/sala.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome ou identificador da turma, por exemplo: "7A" ou "Turma 2026".
    @Column(nullable = false)
    private String name;

    // Série escolar a qual a turma pertence, como Fundamental II ou Médio.
    @ManyToOne(optional = false)
    @JoinColumn(name = "ano_escolar", nullable = false)
    private SchoolGrade schoolGrade;

    // Ano acadêmico da turma, como 2026.
    @Column(name = "ano_academico", nullable = false)
    private Integer academicYear;
}
