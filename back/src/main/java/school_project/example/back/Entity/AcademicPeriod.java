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
@Table(name = "academic_periods")
public class AcademicPeriod {

    // Identificador único do período acadêmico.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do período, por exemplo: "1º Semestre" ou "2026".
    private String name;

    // Número ou ordem do período dentro do calendário acadêmico.
    private Integer number;
}