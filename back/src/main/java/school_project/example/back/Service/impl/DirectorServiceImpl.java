package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.Director.DirectorRequestDTO;
import school_project.example.back.DTO.users.Director.DirectorResponseDTO;
import school_project.example.back.Entity.Director;
import school_project.example.back.Entity.User;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.DirectorMapper;
import school_project.example.back.Repository.DirectorRepository;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Service.DirectorService;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public DirectorResponseDTO createDirector(DirectorRequestDTO dto) {
        if (dto.getUser().getPassword() == null
                || dto.getUser().getPassword().isBlank()) {

            throw new InvalidDataException(
                    "A senha é obrigatória.");
        }

        if (directorRepository.existsByMatricula(dto.getMatricula())) {
            throw new DuplicateResourceException(
                    "Já existe um diretor com essa matrícula.");
        }

        if (userRepository.existsByEmail(dto.getUser().getEmail())) {
            throw new DuplicateResourceException(
                    "Já existe um usuário com esse email.");
        }

        Director director = DirectorMapper.mapToEntity(dto);

        director.getUser().setPassword(
                passwordEncoder.encode(
                        director.getUser().getPassword()));

        Director saved = directorRepository.save(director);

        return DirectorMapper.mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public DirectorResponseDTO getDirector(Long id) {
        return DirectorMapper.mapToResponseDTO(findDirector(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DirectorResponseDTO getDirectorByMatricula(String matricula) {
        return DirectorMapper.mapToResponseDTO(findByMatricula(matricula));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectorResponseDTO> getAllDirectors() {

        return directorRepository.findAll()
                .stream()
                .map(DirectorMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public DirectorResponseDTO updateDirector(
            Long id,
            DirectorRequestDTO dto) {

        return update(findDirector(id), dto);
    }

    @Override
    @Transactional
    public DirectorResponseDTO updateDirectorByMatricula(
            String matricula,
            DirectorRequestDTO dto) {

        return update(findByMatricula(matricula), dto);
    }

    @Override
    @Transactional
    public void deleteDirector(Long id) {
        directorRepository.delete(findDirector(id));
    }

    @Override
    @Transactional
    public void deleteDirectorByMatricula(String matricula) {
        directorRepository.delete(findByMatricula(matricula));
    }

    private DirectorResponseDTO update(
            Director director,
            DirectorRequestDTO dto) {

        if (!director.getMatricula().equals(dto.getMatricula())
                && directorRepository.existsByMatricula(dto.getMatricula())) {

            throw new DuplicateResourceException(
                    "Já existe um diretor com essa matrícula.");
        }

        User user = director.getUser();

        if (!user.getEmail().equalsIgnoreCase(dto.getUser().getEmail())
                && userRepository.existsByEmail(dto.getUser().getEmail())) {

            throw new DuplicateResourceException(
                    "Já existe um usuário com esse email.");
        }

        director.setMatricula(dto.getMatricula());

        user.setFirstName(dto.getUser().getFirstName());
        user.setLastName(dto.getUser().getLastName());
        user.setEmail(dto.getUser().getEmail());

        if (dto.getUser().getPassword() != null
                && !dto.getUser().getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            dto.getUser().getPassword()));
        }

        Director saved = directorRepository.save(director);

        return DirectorMapper.mapToResponseDTO(saved);
    }

    private Director findDirector(Long id) {

        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Diretor não encontrado com id: " + id));
    }

    private Director findByMatricula(String matricula) {

        return directorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Diretor não encontrado com matrícula: "
                                + matricula));
    }
}