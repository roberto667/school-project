package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeRequestDTO;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeResponseDTO;
import school_project.example.back.Entity.enums.Grau;
import school_project.example.back.Service.SchoolGradeService;

@RestController
@RequestMapping("/api/school-grades")
@RequiredArgsConstructor
public class SchoolGradeController {

    private final SchoolGradeService schoolGradeService;

    @PostMapping
    public ResponseEntity<SchoolGradeResponseDTO> create(
            @Valid @RequestBody SchoolGradeRequestDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(schoolGradeService.createSchoolGrade(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolGradeResponseDTO> get(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                schoolGradeService.getSchoolGrade(id));
    }

    @GetMapping
    public ResponseEntity<List<SchoolGradeResponseDTO>> getAll() {

        return ResponseEntity.ok(
                schoolGradeService.getAllSchoolGrades());
    }

    @GetMapping("/search")
    public ResponseEntity<SchoolGradeResponseDTO> getByGradeAndYear(
            @RequestParam Grau grau,
            @RequestParam Integer ano) {

        return ResponseEntity.ok(
                schoolGradeService.getSchoolGrade(grau, ano));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SchoolGradeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SchoolGradeRequestDTO dto) {

        return ResponseEntity.ok(
                schoolGradeService.updateSchoolGrade(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        schoolGradeService.deleteSchoolGrade(id);

        return ResponseEntity.noContent().build();
    }
}