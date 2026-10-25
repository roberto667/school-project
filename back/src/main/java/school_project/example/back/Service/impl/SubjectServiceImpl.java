package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.Subject.SubjectRequestDTO;
import school_project.example.back.DTO.school.Subject.SubjectResponseDTO;
import school_project.example.back.Entity.Subject;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.SubjectMapper;
import school_project.example.back.Repository.SubjectRepository;
import school_project.example.back.Service.SubjectService;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    @Transactional
    public SubjectResponseDTO createSubject(SubjectRequestDTO dto) {

        if (subjectRepository.existsByNomeIgnoreCase(dto.getNome())) {
            throw new DuplicateResourceException(
                    "Já existe uma disciplina com esse nome.");
        }

        Subject subject = SubjectMapper.mapToEntity(dto);

        return SubjectMapper.mapToResponseDTO(
                subjectRepository.save(subject));
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubject(Long id) {
        return SubjectMapper.mapToResponseDTO(findSubject(id));
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getSubjectByNome(String nome) {

        Subject subject = subjectRepository
                .findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Disciplina não encontrada: " + nome));

        return SubjectMapper.mapToResponseDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getAllSubjects() {

        return subjectRepository.findAll()
                .stream()
                .map(SubjectMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public SubjectResponseDTO updateSubject(
            Long id,
            SubjectRequestDTO dto) {

        Subject subject = findSubject(id);

        if (!subject.getNome().equalsIgnoreCase(dto.getNome())
                && subjectRepository.existsByNomeIgnoreCase(dto.getNome())) {

            throw new DuplicateResourceException(
                    "Já existe uma disciplina com esse nome.");
        }

        subject.setNome(dto.getNome());

        return SubjectMapper.mapToResponseDTO(
                subjectRepository.save(subject));
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        subjectRepository.delete(findSubject(id));
    }

    private Subject findSubject(Long id) {

        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Disciplina não encontrada com id: " + id));
    }
}