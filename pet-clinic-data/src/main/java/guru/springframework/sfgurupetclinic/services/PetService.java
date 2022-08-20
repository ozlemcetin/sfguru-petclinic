package guru.springframework.sfgurupetclinic.services;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;

import java.util.Set;

public interface PetService {


    Set<Pet> findAll();

    Pet findById(Long id);

    Pet save(Pet object);
}
