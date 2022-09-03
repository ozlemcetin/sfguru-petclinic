package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Visit;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.springframework.stereotype.Service;

@Service
public class VisitServiceMap extends CrudServiceMap<Visit, Long> implements VisitService {

    @Override
    public Visit save(Visit visit) {

        if (visit.getPet() == null || visit.getPet().getId() == null || visit.getPet().getOwner() == null || visit.getPet().getOwner().getId() == null) {
            throw new RuntimeException("Invalid visit object, cannot be saved!");
        }

        return super.save(visit);
    }
}
