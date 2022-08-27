package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends CrudServiceMap<Vet, Long> implements VetService {


}
