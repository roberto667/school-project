package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import school_project.example.back.DTO.users.DirectorDTO;
import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.DTO.users.Student.StudentRequestDTO;
import school_project.example.back.DTO.users.User.UserDTO;
import school_project.example.back.Service.UserService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    // ---------- STUDENT ----------

    @PostMapping("/students")
    public ResponseEntity<StudentRequestDTO> createStudent(@Valid @RequestBody StudentRequestDTO studentDTO) {
        StudentRequestDTO created = userService.createStudent(studentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/students/update/{id}")
    public ResponseEntity<StudentRequestDTO> updateStudent(@PathVariable Long id,
            @RequestBody StudentRequestDTO studentDTO) {
        StudentRequestDTO updated = userService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentRequestDTO> getStudent(@PathVariable Long id) {
        StudentRequestDTO student = userService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentRequestDTO>> getAllStudents() {
        List<StudentRequestDTO> students = userService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // ---------- STUDENT (por matrícula) ----------

    @GetMapping("/students/matricula/{matricula}")
    public ResponseEntity<StudentRequestDTO> getStudentByMatricula(@PathVariable String matricula) {
        StudentRequestDTO student = userService.getStudentByMatricula(matricula);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/students/matricula/{matricula}")
    public ResponseEntity<StudentRequestDTO> updateStudentByMatricula(
            @PathVariable String matricula, @RequestBody StudentRequestDTO studentDTO) {
        StudentRequestDTO updated = userService.updateStudentByMatricula(matricula, studentDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/students/matricula/{matricula}")
    public ResponseEntity<Void> deleteStudentByMatricula(@PathVariable String matricula) {
        userService.deleteStudentByMatricula(matricula);
        return ResponseEntity.noContent().build();
    }

    // ---------- TEACHER ----------

    @PostMapping("/teachers")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO created = userService.createTeacher(teacherDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        TeacherDTO teacher = userService.getTeacher(id);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> teachers = userService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    // ---------- TEACHER (por matrícula) ----------

    @GetMapping("/teachers/matricula/{matricula}")
    public ResponseEntity<TeacherDTO> getTeacherByMatricula(@PathVariable String matricula) {
        TeacherDTO teacher = userService.getTeacherByMatricula(matricula);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping("/teachers/matricula/{matricula}")
    public ResponseEntity<TeacherDTO> updateTeacherByMatricula(
            @PathVariable String matricula, @RequestBody TeacherDTO teacherDTO) {
        TeacherDTO updated = userService.updateTeacherByMatricula(matricula, teacherDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/teachers/matricula/{matricula}")
    public ResponseEntity<Void> deleteTeacherByMatricula(@PathVariable String matricula) {
        userService.deleteTeacherByMatricula(matricula);
        return ResponseEntity.noContent().build();
    }

    // ---------- DIRECTOR ----------

    @PostMapping("/directors")
    public ResponseEntity<DirectorDTO> createDirector(@Valid @RequestBody DirectorDTO directorDTO) {
        DirectorDTO created = userService.createDirector(directorDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/directors/{id}")
    public ResponseEntity<DirectorDTO> getDirector(@PathVariable Long id) {
        DirectorDTO director = userService.getDirector(id);
        return ResponseEntity.ok(director);
    }

    @GetMapping("/directors")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        List<DirectorDTO> directors = userService.getAllDirectors();
        return ResponseEntity.ok(directors);
    }

    // ---------- DIRECTOR (por matrícula) ----------

    @GetMapping("/directors/matricula/{matricula}")
    public ResponseEntity<DirectorDTO> getDirectorByMatricula(@PathVariable String matricula) {
        DirectorDTO director = userService.getDirectorByMatricula(matricula);
        return ResponseEntity.ok(director);
    }

    @PutMapping("/directors/matricula/{matricula}")
    public ResponseEntity<DirectorDTO> updateDirectorByMatricula(
            @PathVariable String matricula, @RequestBody DirectorDTO directorDTO) {
        DirectorDTO updated = userService.updateDirectorByMatricula(matricula, directorDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/directors/matricula/{matricula}")
    public ResponseEntity<Void> deleteDirectorByMatricula(@PathVariable String matricula) {
        userService.deleteDirectorByMatricula(matricula);
        return ResponseEntity.noContent().build();
    }

    // ---------- USER (genérico) ----------

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updated = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}