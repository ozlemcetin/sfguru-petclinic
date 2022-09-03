package guru.springframework.sfgurupetclinic.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    /*
    ===
     */

     /*
        We don't do any cascading here.
        If we delete a pet  we don't want to cascade the delete operation to the owner
     */

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

     /*
        We don't need a bidirectional relationship.
        This is a unidirectional relationship from Pet to PetType.
        We DO NOT cascade persistence events form Pet to PetType.
     */

    @OneToOne
    @JoinColumn(name = "type_id")
    private PetType petType;



    /*
         Use @OneToMany to set up a relationship mapping.
         CascadeType.ALL means if we are to delete a pet, that's going to cascade down on visits.
     */

     /*
       Pet owns the Visit.
       For mappedBy, use the target property on the child class.
       This Pet will get stored on the child object
       The Visit has a property called pet.
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();


    /*
    ===
     */

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Set<Visit> visits) {
        this.visits = visits;
    }
}
