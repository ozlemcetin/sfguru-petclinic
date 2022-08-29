package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.Speciality;
import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.SpecialityService;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.stereotype.Service;

@Service
public class VetServiceMap extends CrudServiceMap<Vet, Long> implements VetService {

    private final SpecialityService specialityService;

    public VetServiceMap(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }


    @Override
    public Vet save(Vet object) {

        Vet savedVet = super.save(object);

        if (savedVet.getSpecialities() != null && savedVet.getSpecialities().size() > 0) {

            savedVet.getSpecialities().forEach(speciality -> {

                //if not saved already
                if (speciality.getId() == null) {

                    Speciality savedSpeciality = specialityService.save(speciality);
                    speciality.setId(savedSpeciality.getId());
                }

            });
        }

        return savedVet;
    }
}
