package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeRequestDTO;
import school_project.example.back.DTO.school.SchoolGrade.SchoolGradeResponseDTO;
import school_project.example.back.Entity.SchoolGrade;
import school_project.example.back.Entity.enums.Grau;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.SchoolGradeMapper;
import school_project.example.back.Repository.SchoolGradeRepository;
import school_project.example.back.Service.SchoolGradeService;

@Service
@RequiredArgsConstructor
public class SchoolGradeServiceImpl implements SchoolGradeService {

    private final SchoolGradeRepository schoolGradeRepository;

    @Override
    @Transactional
    public SchoolGradeResponseDTO createSchoolGrade(
            SchoolGradeRequestDTO dto) {

        validateYear(dto.getGrau(), dto.getAno());

        if (schoolGradeRepository.existsByGrauAndAno(
                dto.getGrau(),
                dto.getAno())) {

            throw new DuplicateResourceException(
                    "Esse grau e ano escolar já estão cadastrados.");
        }

        SchoolGrade schoolGrade = SchoolGradeMapper.mapToEntity(dto);

        return SchoolGradeMapper.mapToResponseDTO(
                schoolGradeRepository.save(schoolGrade));
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolGradeResponseDTO getSchoolGrade(Long id) {
        return SchoolGradeMapper.mapToResponseDTO(findGrade(id));
    }

    @Override
    @Transactional(readOnly = true)
    public SchoolGradeResponseDTO getSchoolGrade(
            Grau grau,
            Integer ano) {

        SchoolGrade schoolGrade = schoolGradeRepository
                .findByGrauAndAno(grau, ano)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grau escolar não encontrado."));

        return SchoolGradeMapper.mapToResponseDTO(schoolGrade);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolGradeResponseDTO> getAllSchoolGrades() {

        return schoolGradeRepository.findAll()
                .stream()
                .map(SchoolGradeMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public SchoolGradeResponseDTO updateSchoolGrade(
            Long id,
            SchoolGradeRequestDTO dto) {

        SchoolGrade schoolGrade = findGrade(id);

        validateYear(dto.getGrau(), dto.getAno());

        boolean changed = schoolGrade.getGrau() != dto.getGrau()
                || !schoolGrade.getAno().equals(dto.getAno());

        if (changed
                && schoolGradeRepository.existsByGrauAndAno(
                        dto.getGrau(),
                        dto.getAno())) {

            throw new DuplicateResourceException(
                    "Esse grau e ano escolar já estão cadastrados.");
        }

        schoolGrade.setGrau(dto.getGrau());
        schoolGrade.setAno(dto.getAno());

        return SchoolGradeMapper.mapToResponseDTO(
                schoolGradeRepository.save(schoolGrade));
    }

    @Override
    @Transactional
    public void deleteSchoolGrade(Long id) {
        schoolGradeRepository.delete(findGrade(id));
    }

    private SchoolGrade findGrade(Long id) {

        return schoolGradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grau escolar não encontrado com id: " + id));
    }

    private void validateYear(Grau grau, Integer ano) {

        if (grau == null || ano == null) {
            throw new InvalidDataException(
                    "Grau e ano são obrigatórios.");
        }

        int max = switch (grau) {
            case FUNDAMENTAL_1 -> 5;
            case FUNDAMENTAL_2 -> 4;
            case MEDIO -> 3;
            case GRADUACAO -> 20;
        };

        if (ano < 1 || ano > max) {
            throw new InvalidDataException(
                    "Ano " + ano + " inválido para " + grau
                            + ". Valores aceitos: 1 a " + max);
        }
    }
}