package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class OwnerServiceMap extends CrudServiceMap<Owner, Long> implements OwnerService {

    @Override
    public Owner findByLastName(String lastName) {

        Set<Owner> set = findAll();

        Optional<Owner> ownerOptional = set.stream()

                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))

                .findFirst();

        return ownerOptional.orElse(null);
    }
}
