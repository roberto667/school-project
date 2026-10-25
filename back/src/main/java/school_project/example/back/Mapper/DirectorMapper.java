package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.Director.DirectorRequestDTO;
import school_project.example.back.DTO.users.Director.DirectorResponseDTO;
import school_project.example.back.Entity.Director;

/**
 * Converte a entidade Director para DTO e vice-versa,
 * cuidando da matrícula e do usuário associado ao diretor.
 */
public class DirectorMapper {

    public static DirectorResponseDTO mapToResponseDTO(Director director) {

        if (director == null)
            return null;

        DirectorResponseDTO dto = new DirectorResponseDTO();

        dto.setId(director.getId());
        dto.setMatricula(director.getMatricula());
        dto.setUser(
                UserMapper.mapToResponseDTO(director.getUser()));

        return dto;
    }

    public static Director mapToEntity(DirectorRequestDTO dto) {

        if (dto == null)
            return null;

        Director director = new Director();

        director.setMatricula(dto.getMatricula());
        director.setUser(
                UserMapper.mapToEntity(dto.getUser()));

        return director;
    }
}