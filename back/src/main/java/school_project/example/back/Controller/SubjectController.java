package school_project.example.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Subject.SubjectRequestDTO;
import school_project.example.back.DTO.school.Subject.SubjectResponseDTO;
import school_project.example.back.Service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

        private final SubjectService subjectService;

        @PostMapping
        public ResponseEntity<SubjectResponseDTO> create(
                        @Valid @RequestBody SubjectRequestDTO dto) {

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(subjectService.createSubject(dto));
        }

        @GetMapping("/{id}")
        public ResponseEntity<SubjectResponseDTO> get(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                subjectService.getSubject(id));
        }

        @GetMapping
        public ResponseEntity<List<SubjectResponseDTO>> getAll() {

                return ResponseEntity.ok(
                                subjectService.getAllSubjects());
        }

        @GetMapping("/name/{nome}")
        public ResponseEntity<SubjectResponseDTO> getByNome(
                        @PathVariable String nome) {

                return ResponseEntity.ok(
                                subjectService.getSubjectByNome(nome));
        }

        @PutMapping("/{id}")
        public ResponseEntity<SubjectResponseDTO> update(
                        @PathVariable Long id,
                        @Valid @RequestBody SubjectRequestDTO dto) {

                return ResponseEntity.ok(
                                subjectService.updateSubject(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(
                        @PathVariable Long id) {

                subjectService.deleteSubject(id);

                return ResponseEntity.noContent().build();
        }
}