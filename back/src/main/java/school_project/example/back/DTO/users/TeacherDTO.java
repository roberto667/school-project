package school_project.example.back.DTO.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private Long id;
    private UserDTO user;
    private String matricula;
    private String disciplina; // ex: "Matemática", "Português"
}
