package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.model.Visit;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {

    private final PetTypeService petTypeService;
    private final VisitService visitService;

    public PetServiceMap(PetTypeService petTypeService, VisitService visitService) {
        this.petTypeService = petTypeService;
        this.visitService = visitService;
    }


    @Override
    public Pet save(Pet pet) {

        if (pet.getOwner() == null || pet.getOwner().getId() == null)
            throw new RuntimeException("Owner is required to save the pet object!");

        if (pet.getPetType() == null || pet.getPetType().getId() == null)
            throw new RuntimeException("Pet Type is required to save the pet object!");

        Pet savedPet = super.save(pet);

        if (savedPet.getVisits() != null && savedPet.getVisits().size() > 0) {

            savedPet.getVisits().forEach(visit -> {

                //if not saved already
                if (visit.getId() == null) {

                    Visit savedVisit = visitService.save(visit);
                    visit.setId(savedVisit.getId());
                }
            });
        }

        return savedPet;
    }
}
