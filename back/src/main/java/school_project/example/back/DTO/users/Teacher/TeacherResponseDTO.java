package school_project.example.back.DTO.users.Teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.users.User.UserResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com os dados do professor e do usuário associado.
public class TeacherResponseDTO {

    // Identificador único do professor.
    private Long id;

    // Matrícula profissional do professor.
    private String matricula;

    // Dados do usuário vinculado ao professor.
    private UserResponseDTO user;
}