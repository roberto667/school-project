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
@Table(name = "boletim")
public class Note {

    // Identificador único do registro do boletim.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Aluno ao qual esse boletim pertence.
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Nome da disciplina vinculada às notas.
    @Column(name = "disciplina", nullable = false)
    private String disciplina; // ex: "Matemática", "Português"

    // Ano letivo referente ao boletim.
    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo; // ex: 2026

    // Notas do aluno em cada etapa ou bimestre.
    @Column(name = "nota1")
    private Double nota1;

    @Column(name = "nota2")
    private Double nota2;

    @Column(name = "nota3")
    private Double nota3;

    @Column(name = "nota4")
    private Double nota4;

    // Notas de recuperação para cada etapa.
    @Column(name = "rec1")
    private Double rec1;

    @Column(name = "rec2")
    private Double rec2;

    @Column(name = "rec3")
    private Double rec3;

    @Column(name = "rec4")
    private Double rec4;

    // Nota final de recuperação, quando utilizada.
    @Column(name = "rec_final")
    private Double recFinal;
}