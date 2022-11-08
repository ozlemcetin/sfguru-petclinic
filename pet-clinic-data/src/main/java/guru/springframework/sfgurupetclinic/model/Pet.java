package guru.springframework.sfgurupetclinic.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {

    @Column(name = "name")
    private String name;

    /*
        Declaring the same date pattern form html/thymleaf on Pet birthdate.
     */
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /*
    ===
     */

     /*
        We don't need a bidirectional relationship.
        This is a unidirectional relationship from Pet to PetType.
        We DO NOT cascade persistence events form Pet to PetType.
     */

    @OneToOne
    @JoinColumn(name = "type_id")
    private PetType petType;


     /*
        We don't do any cascading here.
        If we delete a pet  we don't want to cascade the delete operation to the owner
     */

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

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

    @Builder
    public Pet(Long id, String name, LocalDate birthDate, PetType petType, Owner owner, Set<Visit> visits) {
        super(id);
        this.name = name;
        this.birthDate = birthDate;
        this.petType = petType;
        this.owner = owner;
        if (visits != null && visits.size() > 0) this.visits = visits;
    }
}
