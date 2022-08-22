package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.PetService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {


    @Override
    public Pet save(Pet object) {
        return super.save(object.getId(), object);
    }
}
