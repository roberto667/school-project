package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.SchoolGrade;
import school_project.example.back.Entity.enums.Grau;

/**
 * Repositório para acesso aos níveis escolares da instituição.
 * Permite consultar etapas por grau e ano letivo.
 */
@Repository
public interface SchoolGradeRepository
                extends JpaRepository<SchoolGrade, Long> {

        Optional<SchoolGrade> findByGrauAndAno(
                        Grau grau,
                        Integer ano);

        boolean existsByGrauAndAno(
                        Grau grau,
                        Integer ano);
}