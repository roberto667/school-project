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
import school_project.example.back.DTO.users.StudentDTO;
import school_project.example.back.DTO.users.TeacherDTO;
import school_project.example.back.DTO.users.UserDTO;
import school_project.example.back.Service.UserService;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    // ---------- STUDENT ----------

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO created = userService.createStudent(studentDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        StudentDTO student = userService.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = userService.getAllStudents();
        return ResponseEntity.ok(students);
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
