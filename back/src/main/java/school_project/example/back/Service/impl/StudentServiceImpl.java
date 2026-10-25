package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.Student.StudentResponseDTO;
import school_project.example.back.Entity.Student;
import school_project.example.back.Entity.User;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.StudentMapper;
import school_project.example.back.Repository.StudentRepository;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

        private final StudentRepository studentRepository;
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;

        @Override
        @Transactional
        public StudentResponseDTO createStudent(StudentRequestDTO dto) {

                // 1. Validação da senha
                if (dto.getUser().getPassword() == null ||
                                dto.getUser().getPassword().isBlank()) {

                        throw new InvalidDataException(
                                        "A senha é obrigatória.");
                }

                // 2. Matrícula duplicada
                if (studentRepository.existsByMatricula(dto.getMatricula())) {
                        throw new DuplicateResourceException(
                                        "Matrícula já cadastrada.");
                }

                // 3. Email duplicado
                if (userRepository.existsByEmail(dto.getUser().getEmail())) {
                        throw new DuplicateResourceException(
                                        "Email já cadastrado.");
                }

                // 4. DTO -> Entity
                Student student = StudentMapper.mapToEntity(dto);

                User user = student.getUser();

                // 5. Criptografa senha
                user.setPassword(
                                passwordEncoder.encode(user.getPassword()));

                // 6. PRIMEIRO salva User
                User savedUser = userRepository.save(user);

                // 7. Relaciona Student ao User persistido
                student.setUser(savedUser);

                // 8. Agora salva Student
                Student savedStudent = studentRepository.save(student);

                return StudentMapper.mapToResponseDTO(
                                savedStudent);
        }

        @Override
        @Transactional(readOnly = true)
        public StudentResponseDTO getStudent(Long studentId) {
                return StudentMapper.mapToResponseDTO(
                                findStudent(studentId));
        }

        @Override
        @Transactional(readOnly = true)
        public StudentResponseDTO getStudentByMatricula(String matricula) {

                Student student = studentRepository
                                .findByMatricula(matricula)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado com matrícula: "
                                                                + matricula));

                return StudentMapper.mapToResponseDTO(student);
        }

        @Override
        @Transactional(readOnly = true)
        public List<StudentResponseDTO> getAllStudents() {

                return studentRepository
                                .findByUserActiveTrue()
                                .stream()
                                .map(StudentMapper::mapToResponseDTO)
                                .toList();
        }

        @Override
        @Transactional
        public StudentResponseDTO updateStudent(
                        Long studentId,
                        StudentRequestDTO dto) {

                Student student = findStudent(studentId);

                return update(student, dto);
        }

        @Override
        @Transactional
        public StudentResponseDTO updateStudentByMatricula(
                        String matricula,
                        StudentRequestDTO dto) {

                Student student = studentRepository
                                .findByMatricula(matricula)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado com matrícula: "
                                                                + matricula));

                return update(student, dto);
        }

        @Override
        @Transactional
        public void activateStudent(String matricula) {

                Student student = studentRepository
                                .findByMatricula(matricula)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado."));

                student.getUser().setActive(true);

                userRepository.save(student.getUser());
        }

        @Override
        @Transactional
        public void deleteStudent(Long studentId) {

                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado."));

                User user = student.getUser();

                user.setActive(false);

                userRepository.save(user);
        }

        @Override
        @Transactional
        public void deleteStudentByMatricula(String matricula) {

                Student student = studentRepository
                                .findByMatricula(matricula)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado com matrícula: "
                                                                + matricula));
                User user = student.getUser();

                user.setActive(false);

                userRepository.save(user);

        }

        private StudentResponseDTO update(
                        Student student,
                        StudentRequestDTO dto) {

                if (!student.getMatricula().equals(dto.getMatricula())
                                && studentRepository.existsByMatricula(dto.getMatricula())) {

                        throw new DuplicateResourceException(
                                        "Já existe um aluno com essa matrícula.");
                }

                User user = student.getUser();

                if (!user.getEmail().equalsIgnoreCase(dto.getUser().getEmail())
                                && userRepository.existsByEmail(dto.getUser().getEmail())) {

                        throw new DuplicateResourceException(
                                        "Já existe um usuário com esse email.");
                }

                student.setMatricula(dto.getMatricula());

                user.setFirstName(dto.getUser().getFirstName());
                user.setLastName(dto.getUser().getLastName());
                user.setEmail(dto.getUser().getEmail());

                // Senha vazia no PUT = mantém a senha atual
                if (dto.getUser().getPassword() != null
                                && !dto.getUser().getPassword().isBlank()) {

                        user.setPassword(
                                        passwordEncoder.encode(
                                                        dto.getUser().getPassword()));
                }

                Student saved = studentRepository.save(student);

                return StudentMapper.mapToResponseDTO(saved);
        }

        private Student findStudent(Long id) {

                return studentRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Aluno não encontrado com id: " + id));
        }

}