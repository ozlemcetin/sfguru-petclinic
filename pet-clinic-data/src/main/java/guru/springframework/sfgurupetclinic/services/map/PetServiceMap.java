package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.services.PetService;
import org.springframework.stereotype.Service;

@Service
public class PetServiceMap extends CrudServiceMap<Pet, Long> implements PetService {

}
