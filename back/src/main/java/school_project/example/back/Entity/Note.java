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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "disciplina", nullable = false)
    private String disciplina; // ex: "Matemática", "Português"

    @Column(name = "ano_letivo", nullable = false)
    private Integer anoLetivo; // ex: 2026

    @Column(name = "nota1")
    private Double nota1;

    @Column(name = "nota2")
    private Double nota2;

    @Column(name = "nota3")
    private Double nota3;

    @Column(name = "nota4")
    private Double nota4;

    @Column(name = "rec1")
    private Double rec1;

    @Column(name = "rec2")
    private Double rec2;

    @Column(name = "rec3")
    private Double rec3;

    @Column(name = "rec4")
    private Double rec4;

    @Column(name = "rec_final")
    private Double recFinal;
}