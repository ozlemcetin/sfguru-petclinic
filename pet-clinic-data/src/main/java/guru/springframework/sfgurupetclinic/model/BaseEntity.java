package guru.springframework.sfgurupetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    /*
        This is the id calues
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
