package bg.softuni.gameStore.models;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity {
    private Long id;

    protected BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
