package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Speciality;
import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.SpecialityService;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class SpecialityServiceMap extends CrudServiceMap<Speciality, Long> implements SpecialityService {


}
