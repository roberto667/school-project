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

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.maptoStudent(studentDTO);
        student.getUser().setPassword(passwordEncoder.encode(student.getUser().getPassword()));
        Student saved = studentRepository.save(student);
        return StudentMapper.maptoStudentDTO(saved);
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
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User não encontrado com id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}