package guru.springframework.sfgurupetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/*
    @MappedSuperclass establishes this class as a base class in JPA.
    We don't need this class mapped to the database.
    We expect other classes to inherit from this class.
 */
@MappedSuperclass
public class BaseEntity implements Serializable {

    /*
        This is the id calues
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    ===
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
