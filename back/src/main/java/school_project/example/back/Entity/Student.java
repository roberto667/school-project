package school_project.example.back.Entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school_project.example.back.Entity.enums.Grau;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matricula", nullable = false, unique = true)
    private String matricula;
    @Enumerated(EnumType.STRING)
    @Column(name = "grau", nullable = false)
    private Grau grau;
    @Column(name = "ano", nullable = false)
    private Integer ano;

    // Aqui está a mágica da Chave Estrangeira:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Note> boletins;
}