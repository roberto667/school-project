package school_project.example.back.Mapper;

import school_project.example.back.DTO.users.DirectorDTO;
import school_project.example.back.Entity.Director;

public class DirectorMapper {
    public static DirectorDTO maptoDirectorDTO(Director director) {
        if (director == null)
            return null;
        DirectorDTO dto = new DirectorDTO();
        dto.setId(director.getId());
        dto.setUser(UserMapper.maptoUserDTO(director.getUser()));
        dto.setMatricula(director.getMatricula());
        return dto;
    }

    public static Director maptoDirector(DirectorDTO directorDTO) {
        if (directorDTO == null)
            return null;
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setMatricula(directorDTO.getMatricula());
        director.setUser(UserMapper.maptoUser(directorDTO.getUser()));
        return director;
    }
}