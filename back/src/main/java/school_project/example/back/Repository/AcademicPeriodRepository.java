package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.AcademicPeriod;

/**
 * Repositório para acesso aos períodos letivos da escola.
 * Permite buscar, validar e consultar períodos por número ou nome.
 */
@Repository
public interface AcademicPeriodRepository
        extends JpaRepository<AcademicPeriod, Long> {

    Optional<AcademicPeriod> findByNumber(Integer number);

    Optional<AcademicPeriod> findByNameIgnoreCase(String name);

    boolean existsByNumber(Integer number);
}