package school_project.example.back.DTO.users.Student;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.users.User.UserResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com os dados do aluno e do usuário associado.
public class StudentResponseDTO {

    // Identificador único do aluno.
    private Long id;

    // Matrícula acadêmica do aluno.
    private String matricula;

    // Dados básicos do usuário vinculado ao aluno.
    private UserResponseDTO user;
}