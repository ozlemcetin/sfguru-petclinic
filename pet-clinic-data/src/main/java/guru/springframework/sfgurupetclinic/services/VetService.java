package guru.springframework.sfgurupetclinic.services;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.Vet;

import java.util.Set;

public interface VetService {


    Set<Vet> findAll();

    Vet findById(Long id);

    Vet save(Vet object);
}
