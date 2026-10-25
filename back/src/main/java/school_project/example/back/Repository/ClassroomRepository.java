package school_project.example.back.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import school_project.example.back.Entity.Classroom;

/**
 * Repositório para acesso às turmas da escola.
 * Permite localizar turmas por ano letivo e nível escolar.
 */
@Repository
public interface ClassroomRepository
        extends JpaRepository<Classroom, Long> {

    List<Classroom> findByAcademicYear(Integer academicYear);

    List<Classroom> findBySchoolGradeId(Long schoolGradeId);

    List<Classroom> findBySchoolGradeIdAndAcademicYear(
            Long schoolGradeId,
            Integer academicYear);

    boolean existsByNameIgnoreCaseAndSchoolGradeIdAndAcademicYear(
            String name,
            Long schoolGradeId,
            Integer academicYear);
}