package guru.springframework.sfgurupetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vets")
public class Vet extends Person {

    /*
    ===
     */

    /*
     @ManyToMany is by default LAZY initialized.
     Specialities would be null till they are asked for.
     EASY means JPA will load everything at once.

     */
    /*
       For @ManyToMany Relationships specify a common JOIN TABLE with @JoinTable.
       On the other side, we need to tell it   @ManyToMany(mappedBy = "specialities")
    */

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "vet_speciality", joinColumns = @JoinColumn(name = "vet_id"), inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private Set<Speciality> specialities = new HashSet<>();


}
