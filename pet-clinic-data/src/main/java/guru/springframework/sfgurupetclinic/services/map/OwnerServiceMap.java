package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class OwnerServiceMap extends CrudServiceMap<Owner, Long> implements OwnerService {

    @Override
    public Owner save(Owner object) {
        return super.save(object.getId(), object);
    }

    @Override
    public Owner findByLastName(String lastName) {

        Set<Owner> set = findAll();
        List<Owner> owners = set.stream().filter(owner -> owner.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());

        return (owners != null && owners.size() > 0 ? owners.get(0) : null);
    }
}
