package school_project.example.back.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private Long id;
    private Long studentId;
    private String disciplina;
    private Integer anoLetivo;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double nota4;
    private Double rec1;
    private Double rec2;
    private Double rec3;
    private Double rec4;
    private Double recFinal;
}