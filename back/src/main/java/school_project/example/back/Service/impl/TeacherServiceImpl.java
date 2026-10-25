package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.Teacher.TeacherRequestDTO;
import school_project.example.back.DTO.users.Teacher.TeacherResponseDTO;
import school_project.example.back.Entity.Student;
import school_project.example.back.Entity.Teacher;
import school_project.example.back.Entity.User;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.TeacherMapper;
import school_project.example.back.Repository.TeacherRepository;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Service.TeacherService;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public TeacherResponseDTO createTeacher(TeacherRequestDTO dto) {

        // 1. Senha obrigatória
        if (dto.getUser().getPassword() == null ||
                dto.getUser().getPassword().isBlank()) {

            throw new InvalidDataException(
                    "A senha é obrigatória.");
        }

        // 2. Verifica matrícula duplicada
        if (teacherRepository.existsByMatricula(dto.getMatricula())) {
            throw new DuplicateResourceException(
                    "Matrícula já cadastrada.");
        }

        // 3. Verifica email duplicado
        if (userRepository.existsByEmail(dto.getUser().getEmail())) {
            throw new DuplicateResourceException(
                    "Email já cadastrado.");
        }

        // 4. Converte DTO
        Teacher teacher = TeacherMapper.mapToEntity(dto);

        User user = teacher.getUser();

        // 5. Criptografa senha
        user.setPassword(
                passwordEncoder.encode(user.getPassword()));

        // 6. Salva o User primeiro
        User savedUser = userRepository.save(user);

        // 7. Associa o User persistido
        teacher.setUser(savedUser);

        // 8. Salva o Teacher
        Teacher savedTeacher = teacherRepository.save(teacher);

        // 9. Retorna DTO
        return TeacherMapper.mapToResponseDTO(
                savedTeacher);
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDTO getTeacher(Long teacherId) {
        return TeacherMapper.mapToResponseDTO(findTeacher(teacherId));
    }

    @Override
    @Transactional(readOnly = true)
    public TeacherResponseDTO getTeacherByMatricula(String matricula) {

        Teacher teacher = findByMatricula(matricula);

        return TeacherMapper.mapToResponseDTO(teacher);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeacherResponseDTO> getAllTeachers() {

        return teacherRepository.findByUserActiveTrue()
                .stream()
                .map(TeacherMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public TeacherResponseDTO updateTeacher(
            Long teacherId,
            TeacherRequestDTO dto) {

        return update(findTeacher(teacherId), dto);
    }

    @Override
    @Transactional
    public TeacherResponseDTO updateTeacherByMatricula(
            String matricula,
            TeacherRequestDTO dto) {

        return update(findByMatricula(matricula), dto);
    }

    @Override
    @Transactional
    public void activateTeacher(String matricula) {

        Teacher teacher = teacherRepository
                .findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor não encontrado."));

        teacher.getUser().setActive(true);

        userRepository.save(teacher.getUser());
    }

    @Override
    @Transactional
    public void deleteTeacher(Long teacherId) {
        Teacher teacher = findTeacher(teacherId);
        User user = teacher.getUser();
        user.setActive(false);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteTeacherByMatricula(String matricula) {
        Teacher teacher = findByMatricula(matricula);
        User user = teacher.getUser();
        user.setActive(false);

        userRepository.save(user);
    }

    private TeacherResponseDTO update(
            Teacher teacher,
            TeacherRequestDTO dto) {

        if (!teacher.getMatricula().equals(dto.getMatricula())
                && teacherRepository.existsByMatricula(dto.getMatricula())) {

            throw new DuplicateResourceException(
                    "Já existe um professor com essa matrícula.");
        }

        User user = teacher.getUser();

        if (!user.getEmail().equalsIgnoreCase(dto.getUser().getEmail())
                && userRepository.existsByEmail(dto.getUser().getEmail())) {

            throw new DuplicateResourceException(
                    "Já existe um usuário com esse email.");
        }

        teacher.setMatricula(dto.getMatricula());

        user.setFirstName(dto.getUser().getFirstName());
        user.setLastName(dto.getUser().getLastName());
        user.setEmail(dto.getUser().getEmail());

        if (dto.getUser().getPassword() != null
                && !dto.getUser().getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            dto.getUser().getPassword()));
        }

        Teacher saved = teacherRepository.save(teacher);

        return TeacherMapper.mapToResponseDTO(saved);
    }

    private Teacher findTeacher(Long id) {

        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor não encontrado com id: " + id));
    }

    private Teacher findByMatricula(String matricula) {

        return teacherRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Professor não encontrado com matrícula: "
                                + matricula));
    }
}