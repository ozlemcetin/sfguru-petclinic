package guru.springframework.sfgurupetclinic.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "specialities")
public class Speciality extends BaseEntity {

    private String description;

    /*
    ===
     */

     /*
        specialities is a property on the Vet class.
        On the Vet side, we define a Join Table.
     */

    // @ManyToMany(mappedBy = "specialities")
    // private final Set<Vet> vets = new HashSet<>();


}
