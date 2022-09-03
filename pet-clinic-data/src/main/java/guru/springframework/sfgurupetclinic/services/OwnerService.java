package guru.springframework.sfgurupetclinic.services;

import guru.springframework.sfgurupetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
