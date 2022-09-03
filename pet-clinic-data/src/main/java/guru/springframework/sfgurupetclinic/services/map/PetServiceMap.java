package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.Visit;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {

    private final VisitService visitService;

    public PetServiceMap(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public Pet save(Pet object) {

        Pet savedPet = super.save(object);

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
