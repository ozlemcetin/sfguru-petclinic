package guru.springframework.sfgurupetclinic.model;

import javax.persistence.Entity;
import javax.persistence.Table;

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

    /*
    ===
     */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
