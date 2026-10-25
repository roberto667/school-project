package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Classroom.ClassroomRequestDTO;
import school_project.example.back.DTO.school.Classroom.ClassroomResponseDTO;
import school_project.example.back.Service.ClassroomService;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomResponseDTO> create(
            @Valid @RequestBody ClassroomRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(classroomService.createClassroom(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                classroomService.getClassroom(id));
    }

    @GetMapping
    public ResponseEntity<List<ClassroomResponseDTO>> getAll() {

        return ResponseEntity.ok(
                classroomService.getAllClassrooms());
    }

    @GetMapping("/year/{academicYear}")
    public ResponseEntity<List<ClassroomResponseDTO>> getByYear(
            @PathVariable Integer academicYear) {

        return ResponseEntity.ok(
                classroomService
                        .getClassroomsByAcademicYear(academicYear));
    }

    @GetMapping("/school-grade/{schoolGradeId}")
    public ResponseEntity<List<ClassroomResponseDTO>> getBySchoolGrade(
            @PathVariable Long schoolGradeId) {

        return ResponseEntity.ok(
                classroomService
                        .getClassroomsBySchoolGrade(schoolGradeId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClassroomResponseDTO>> search(
            @RequestParam Long schoolGradeId,
            @RequestParam Integer academicYear) {

        return ResponseEntity.ok(
                classroomService
                        .getClassroomsBySchoolGradeAndAcademicYear(
                                schoolGradeId,
                                academicYear));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ClassroomRequestDTO dto) {

        return ResponseEntity.ok(
                classroomService.updateClassroom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        classroomService.deleteClassroom(id);

        return ResponseEntity.noContent().build();
    }
}