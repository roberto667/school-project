package school_project.example.back.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.Director;

public interface DirectorRepository extends JpaRepository<Director, Long> {
}