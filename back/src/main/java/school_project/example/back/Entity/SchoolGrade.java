package school_project.example.back.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.Grau;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "school_grades")
public class SchoolGrade {

    // Identificador único da etapa escolar.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nível de ensino, como Fundamental I, Fundamental II, Médio ou Graduação.
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grau grau;

    // Ano referente a essa etapa escolar.
    @Column(nullable = false)
    private Integer ano;
}
