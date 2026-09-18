package school_project.example.back.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classes")
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String nome;
    @OneToMany(mappedBy = "class", cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToMany(mappedBy = "class", cascade = CascadeType.ALL)
    private List<Teacher> teachers;
}
