package school_project.example.back.DTO.school.TeachingAssignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;
import school_project.example.back.DTO.school.Subject.SubjectResponseDTO;
import school_project.example.back.DTO.users.Teacher.TeacherResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// DTO de resposta com a atribuição de professor para turma e disciplina.
public class TeachingAssignmentResponseDTO {

    // Identificador único da atribuição de ensino.
    private Long id;

    // Professor responsável pela disciplina.
    private TeacherResponseDTO teacher;

    // Turma em que a disciplina será ministrada.
    private ClassroomResponseDTO classroom;

    // Disciplina vinculada ao professor e à turma.
    private SubjectResponseDTO subject;
}