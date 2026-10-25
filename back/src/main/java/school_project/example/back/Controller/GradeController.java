package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Grade.GradeRequestDTO;
import school_project.example.back.DTO.school.Grade.GradeResponseDTO;
import school_project.example.back.Service.GradeService;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> create(
            @Valid @RequestBody GradeRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(gradeService.createGrade(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                gradeService.getGrade(id));
    }

    @GetMapping
    public ResponseEntity<List<GradeResponseDTO>> getAll() {

        return ResponseEntity.ok(
                gradeService.getAllGrades());
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<GradeResponseDTO>> getByEnrollment(
            @PathVariable Long enrollmentId) {

        return ResponseEntity.ok(
                gradeService.getGradesByEnrollment(enrollmentId));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<List<GradeResponseDTO>> getByAssignment(
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                gradeService
                        .getGradesByTeachingAssignment(assignmentId));
    }

    @GetMapping("/enrollment/{enrollmentId}/assignment/{assignmentId}")
    public ResponseEntity<List<GradeResponseDTO>> getByEnrollmentAndAssignment(
            @PathVariable Long enrollmentId,
            @PathVariable Long assignmentId) {

        return ResponseEntity.ok(
                gradeService
                        .getGradesByEnrollmentAndTeachingAssignment(
                                enrollmentId,
                                assignmentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GradeRequestDTO dto) {

        return ResponseEntity.ok(
                gradeService.updateGrade(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        gradeService.deleteGrade(id);

        return ResponseEntity.noContent().build();
    }
}