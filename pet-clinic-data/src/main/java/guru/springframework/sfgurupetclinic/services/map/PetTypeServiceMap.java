package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"default", "map"})
public class PetTypeServiceMap extends CrudServiceMap<PetType, Long> implements PetTypeService {

}
