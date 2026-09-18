package school_project.example.back.Service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.users.DirectorDTO;
import school_project.example.back.DTO.users.StudentDTO;
import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.DTO.users.UserDTO;
import school_project.example.back.Entity.Director;
import school_project.example.back.Entity.Student;
import school_project.example.back.Entity.Teacher;
import school_project.example.back.Entity.User;
import school_project.example.back.Entity.enums.Grau;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.DirectorMapper;
import school_project.example.back.Mapper.StudentMapper;
import school_project.example.back.Mapper.TeacherMapper;
import school_project.example.back.Mapper.UserMapper;
import school_project.example.back.Repository.DirectorRepository;
import school_project.example.back.Repository.StudentRepository;
import school_project.example.back.Repository.TeacherRepository;
import school_project.example.back.Repository.UserRepository;
import school_project.example.back.Service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DirectorRepository directorRepository;
    private final PasswordEncoder passwordEncoder;

    // ---------- CREATE ----------

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        validarAnoParaGrau(studentDTO.getGrau(), studentDTO.getAno());

        studentDTO.getUser().setPassword(
                passwordEncoder.encode(studentDTO.getUser().getPassword()));

        Student student = StudentMapper.maptoStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.maptoStudentDTO(savedStudent);
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = TeacherMapper.maptoTeacher(teacherDTO);
        teacher.getUser().setPassword(passwordEncoder.encode(teacher.getUser().getPassword()));
        Teacher saved = teacherRepository.save(teacher);
        return TeacherMapper.maptoTeacherDTO(saved);
    }

    @Override
    public DirectorDTO createDirector(DirectorDTO directorDTO) {
        Director director = DirectorMapper.maptoDirector(directorDTO);
        director.getUser().setPassword(passwordEncoder.encode(director.getUser().getPassword()));
        Director saved = directorRepository.save(director);
        return DirectorMapper.maptoDirectorDTO(saved);
    }

    // ---------- GET POR ID ----------

    @Override
    public StudentDTO getStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado com id: " + studentId));
        return StudentMapper.maptoStudentDTO(student);
    }

    @Override
    public TeacherDTO getTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher não encontrado com id: " + teacherId));
        return TeacherMapper.maptoTeacherDTO(teacher);
    }

    @Override
    public DirectorDTO getDirector(Long directorId) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new ResourceNotFoundException("Director não encontrado com id: " + directorId));
        return DirectorMapper.maptoDirectorDTO(director);
    }

    // ---------- GET POR MATRÍCULA ----------

    @Override
    public StudentDTO getStudentByMatricula(String matricula) {
        Student student = studentRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado com matrícula: " + matricula));
        return StudentMapper.maptoStudentDTO(student);
    }

    @Override
    public TeacherDTO getTeacherByMatricula(String matricula) {
        Teacher teacher = teacherRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher não encontrado com matrícula: " + matricula));
        return TeacherMapper.maptoTeacherDTO(teacher);
    }

    @Override
    public DirectorDTO getDirectorByMatricula(String matricula) {
        Director director = directorRepository.findByMatricula(matricula)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Director não encontrado com matrícula: " + matricula));
        return DirectorMapper.maptoDirectorDTO(director);
    }

    // ---------- LISTAGENS ----------

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::maptoStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(TeacherMapper::maptoTeacherDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DirectorDTO> getAllDirectors() {
        return directorRepository.findAll().stream()
                .map(DirectorMapper::maptoDirectorDTO)
                .collect(Collectors.toList());
    }

    // ---------- UPDATE ----------

    @Override
    public UserDTO updateUser(Long userId, UserDTO updateUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User não encontrado com id: " + userId));

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());

        if (updateUser.getPassword() != null && !updateUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        }

        User saved = userRepository.save(user);
        return UserMapper.maptoUserDTO(saved);
    }

    @Override
    public StudentDTO updateStudent(Long studentId, StudentDTO updateStudent) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));

        return aplicarUpdateStudent(student, updateStudent);
    }

    @Override
    public StudentDTO updateStudentByMatricula(String matricula, StudentDTO updateStudent) {
        Student student = studentRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado com matrícula: " + matricula));

        return aplicarUpdateStudent(student, updateStudent);
    }

    @Override
    public TeacherDTO updateTeacherByMatricula(String matricula, TeacherDTO updateTeacher) {
        Teacher teacher = teacherRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher não encontrado com matrícula: " + matricula));

        teacher.setMatricula(updateTeacher.getMatricula());

        if (updateTeacher.getUser() != null) {
            User user = teacher.getUser();
            user.setFirstName(updateTeacher.getUser().getFirstName());
            user.setLastName(updateTeacher.getUser().getLastName());
            user.setEmail(updateTeacher.getUser().getEmail());

            if (updateTeacher.getUser().getPassword() != null && !updateTeacher.getUser().getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updateTeacher.getUser().getPassword()));
            }
        }

        Teacher saved = teacherRepository.save(teacher);
        return TeacherMapper.maptoTeacherDTO(saved);
    }

    @Override
    public DirectorDTO updateDirectorByMatricula(String matricula, DirectorDTO updateDirector) {
        Director director = directorRepository.findByMatricula(matricula)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Director não encontrado com matrícula: " + matricula));

        director.setMatricula(updateDirector.getMatricula());

        if (updateDirector.getUser() != null) {
            User user = director.getUser();
            user.setFirstName(updateDirector.getUser().getFirstName());
            user.setLastName(updateDirector.getUser().getLastName());
            user.setEmail(updateDirector.getUser().getEmail());

            if (updateDirector.getUser().getPassword() != null && !updateDirector.getUser().getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updateDirector.getUser().getPassword()));
            }
        }

        Director saved = directorRepository.save(director);
        return DirectorMapper.maptoDirectorDTO(saved);
    }

    private StudentDTO aplicarUpdateStudent(Student student, StudentDTO updateStudent) {
        validarAnoParaGrau(updateStudent.getGrau(), updateStudent.getAno());

        student.setMatricula(updateStudent.getMatricula());
        student.setGrau(updateStudent.getGrau());
        student.setAno(updateStudent.getAno());

        if (updateStudent.getUser() != null) {
            User user = student.getUser();
            user.setFirstName(updateStudent.getUser().getFirstName());
            user.setLastName(updateStudent.getUser().getLastName());
            user.setEmail(updateStudent.getUser().getEmail());

            if (updateStudent.getUser().getPassword() != null && !updateStudent.getUser().getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updateStudent.getUser().getPassword()));
            }
        }

        Student saved = studentRepository.save(student);
        return StudentMapper.maptoStudentDTO(saved);
    }

    // ---------- DELETE ----------

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User não encontrado com id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteStudentByMatricula(String matricula) {
        Student student = studentRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado com matrícula: " + matricula));

        User user = student.getUser();
        studentRepository.delete(student);
        userRepository.delete(user);
    }

    @Override
    public void deleteTeacherByMatricula(String matricula) {
        Teacher teacher = teacherRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher não encontrado com matrícula: " + matricula));

        User user = teacher.getUser();
        teacherRepository.delete(teacher);
        userRepository.delete(user);
    }

    @Override
    public void deleteDirectorByMatricula(String matricula) {
        Director director = directorRepository.findByMatricula(matricula)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Director não encontrado com matrícula: " + matricula));

        User user = director.getUser();
        directorRepository.delete(director);
        userRepository.delete(user);
    }

    // ---------- VALIDAÇÃO ----------

    private void validarAnoParaGrau(Grau grau, Integer ano) {
        if (grau == null) {
            throw new InvalidDataException("O grau é obrigatório");
        }
        if (ano == null) {
            throw new InvalidDataException("O ano é obrigatório");
        }

        int maxAno = switch (grau) {
            case FUNDAMENTAL_1 -> 5;
            case FUNDAMENTAL_2 -> 4; // 6º ao 9º ano = 1 a 4
            case MEDIO -> 3;
            case GRADUACAO -> 20; // período/semestre, mais flexível
        };

        if (ano < 1 || ano > maxAno) {
            throw new InvalidDataException(
                    "Ano " + ano + " inválido para o grau " + grau + ". Valores aceitos: 1 a " + maxAno);
        }
    }
}