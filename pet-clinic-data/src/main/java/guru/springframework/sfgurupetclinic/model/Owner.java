package guru.springframework.sfgurupetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {


    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "telephone")
    private String telephone;

    /*
    ===
     */

    /*
         Use @OneToMany to set up a relationship mapping.
         CascadeType.ALL means if we are to delete an owner, that's going to cascade down.
     */

     /*
       Owner owns the Pet.
       For mappedBy, use the target property on the child class.
       This Owner will get stored on the child object
       The Pet has a property called owner.
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();

    /*
    ===
     */

    @Builder
    public Owner(Long id, String firstName, String lastName, String address, String city, String telephone, Set<Pet> pets) {
        super(id, firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets != null) this.pets = pets;
    }

    /*
        ===
     */

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {

        if (name == null || name.isEmpty() || name.isBlank()) return null;

        name = name.trim().toLowerCase();
        for (Pet pet : pets) {

            if (ignoreNew && pet.isNew()) {
                //ignore
            } else {
                if (name.equals(pet.getName().toLowerCase())) {
                    return pet;
                }
            }
        }
        return null;
    }
}
