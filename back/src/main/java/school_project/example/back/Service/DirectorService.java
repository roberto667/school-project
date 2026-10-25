package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.users.Director.DirectorRequestDTO;
import school_project.example.back.DTO.users.Director.DirectorResponseDTO;

public interface DirectorService {

    DirectorResponseDTO createDirector(
            DirectorRequestDTO dto);

    DirectorResponseDTO getDirector(Long directorId);

    DirectorResponseDTO getDirectorByMatricula(
            String matricula);

    List<DirectorResponseDTO> getAllDirectors();

    DirectorResponseDTO updateDirector(
            Long directorId,
            DirectorRequestDTO dto);

    DirectorResponseDTO updateDirectorByMatricula(
            String matricula,
            DirectorRequestDTO dto);

    void deleteDirector(Long directorId);

    void deleteDirectorByMatricula(String matricula);
}