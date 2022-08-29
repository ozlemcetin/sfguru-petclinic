package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import org.springframework.stereotype.Service;

@Service
public class PetTypeServiceMap extends CrudServiceMap<PetType, Long> implements PetTypeService {

}
