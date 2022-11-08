package guru.springframework.sfgurupetclinic.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "description")
    private String description;

    /*
    ===
     */

    /*
       We don't do any cascading here.
       If we delete a visit  we don't want to cascade the delete operation to the pet
    */

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    /*
    ===
     */

    public LocalDate getDate() {
        return date;
    }

}
