package school_project.example.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import school_project.example.back.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}
