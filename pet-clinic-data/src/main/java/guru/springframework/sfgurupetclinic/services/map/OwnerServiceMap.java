package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends CrudServiceMap<Owner, Long> implements OwnerService {

    private final PetService petService;

    public OwnerServiceMap(PetService petService) {
        this.petService = petService;
    }


    @Override
    public Owner save(Owner object) {

        Owner savedOwner = super.save(object);

        if (savedOwner.getPets() != null && savedOwner.getPets().size() > 0) {

            savedOwner.getPets().forEach(pet -> {

                //if not saved already
                if (pet.getId() == null) {

                    Pet savedPet = petService.save(pet);
                    pet.setId(savedPet.getId());
                }

            });
        }

        return savedOwner;
    }

    @Override
    public Owner findByLastName(String lastName) {

        Set<Owner> set = findAll();

        Optional<Owner> ownerOptional = set.stream()

                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))

                .findFirst();

        return ownerOptional.orElse(null);
    }
}
