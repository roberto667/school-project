package school_project.example.back.Service;

import java.util.List;

import school_project.example.back.DTO.school.Subject.SubjectRequestDTO;
import school_project.example.back.DTO.school.Subject.SubjectResponseDTO;

public interface SubjectService {

    SubjectResponseDTO createSubject(
            SubjectRequestDTO dto);

    SubjectResponseDTO getSubject(Long subjectId);

    SubjectResponseDTO getSubjectByNome(String nome);

    List<SubjectResponseDTO> getAllSubjects();

    SubjectResponseDTO updateSubject(
            Long subjectId,
            SubjectRequestDTO dto);

    void deleteSubject(Long subjectId);
}