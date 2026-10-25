package school_project.example.back.Service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodRequestDTO;
import school_project.example.back.DTO.school.AcademicPeriod.AcademicPeriodResponseDTO;
import school_project.example.back.Entity.AcademicPeriod;
import school_project.example.back.Exception.DuplicateResourceException;
import school_project.example.back.Exception.InvalidDataException;
import school_project.example.back.Exception.ResourceNotFoundException;
import school_project.example.back.Mapper.AcademicPeriodMapper;
import school_project.example.back.Repository.AcademicPeriodRepository;
import school_project.example.back.Service.AcademicPeriodService;

@Service
@RequiredArgsConstructor
public class AcademicPeriodServiceImpl
        implements AcademicPeriodService {

    private final AcademicPeriodRepository periodRepository;

    @Override
    @Transactional
    public AcademicPeriodResponseDTO createPeriod(
            AcademicPeriodRequestDTO dto) {

        validateNumber(dto.getNumber());

        if (periodRepository.existsByNumber(dto.getNumber())) {
            throw new DuplicateResourceException(
                    "Já existe um período com esse número.");
        }

        AcademicPeriod period = AcademicPeriodMapper.mapToEntity(dto);

        return AcademicPeriodMapper.mapToResponseDTO(
                periodRepository.save(period));
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicPeriodResponseDTO getPeriod(Long id) {
        return AcademicPeriodMapper.mapToResponseDTO(findPeriod(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AcademicPeriodResponseDTO getPeriodByNumber(Integer number) {

        AcademicPeriod period = periodRepository
                .findByNumber(number)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Período acadêmico não encontrado."));

        return AcademicPeriodMapper.mapToResponseDTO(period);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicPeriodResponseDTO> getAllPeriods() {

        return periodRepository.findAll()
                .stream()
                .map(AcademicPeriodMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public AcademicPeriodResponseDTO updatePeriod(
            Long id,
            AcademicPeriodRequestDTO dto) {

        AcademicPeriod period = findPeriod(id);

        validateNumber(dto.getNumber());

        if (!period.getNumber().equals(dto.getNumber())
                && periodRepository.existsByNumber(dto.getNumber())) {

            throw new DuplicateResourceException(
                    "Já existe um período com esse número.");
        }

        period.setName(dto.getName());
        period.setNumber(dto.getNumber());

        return AcademicPeriodMapper.mapToResponseDTO(
                periodRepository.save(period));
    }

    @Override
    @Transactional
    public void deletePeriod(Long id) {
        periodRepository.delete(findPeriod(id));
    }

    private AcademicPeriod findPeriod(Long id) {

        return periodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Período acadêmico não encontrado com id: " + id));
    }

    private void validateNumber(Integer number) {

        if (number == null || number < 1 || number > 4) {
            throw new InvalidDataException(
                    "O número do período deve estar entre 1 e 4.");
        }
    }
}