package school_project.example.back.Mapper;

import school_project.example.back.DTO.school.Subject.SubjectRequestDTO;
import school_project.example.back.DTO.school.Subject.SubjectResponseDTO;
import school_project.example.back.Entity.Subject;

/**
 * Converte a entidade Subject para DTO e vice-versa,
 * mapeando o nome e os dados da disciplina.
 */
public class SubjectMapper {

    public static SubjectResponseDTO mapToResponseDTO(Subject subject) {

        if (subject == null)
            return null;

        SubjectResponseDTO dto = new SubjectResponseDTO();

        dto.setId(subject.getId());
        dto.setNome(subject.getNome());

        return dto;
    }

    public static Subject mapToEntity(SubjectRequestDTO dto) {

        if (dto == null)
            return null;

        Subject subject = new Subject();

        subject.setNome(dto.getNome());

        return subject;
    }
}