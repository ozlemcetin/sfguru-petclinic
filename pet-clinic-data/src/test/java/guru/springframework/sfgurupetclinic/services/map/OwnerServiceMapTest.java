package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerService;
    private Owner owner1;

    @BeforeEach
    void setUp() {

        //As they are hash map implementations
        PetService petService = new PetServiceMap(new VisitServiceMap());
        ownerService = new OwnerServiceMap(petService);

        //Put one owner object into the map
        {
            owner1 = Owner.builder().firstName("Michael").lastName("Weston").address("123 Brickerel").city("Miami").telephone("1231231234").pets(new HashSet<>()).build();
            ownerService.save(owner1);
        }
    }

    @Test
    void findAll() {
        Set<Owner> set = ownerService.findAll();

        //Assertions
        Assertions.assertEquals(set.size(), 1);
        Assertions.assertTrue(set.contains(owner1));
    }

    @Test
    void findById() {
        Owner foundOwner = ownerService.findById(owner1.getId());

        //Assertions
        Assertions.assertNotNull(foundOwner);
        Assertions.assertEquals(foundOwner.getId(), owner1.getId());
    }

    @Test
    void saveWithId() {
        Long id = 2L;
        Owner savedOwner = null;
        {
            Owner owner2 = Owner.builder().id(id).build();
            savedOwner = ownerService.save(owner2);
        }

        //Assertions
        Assertions.assertNotNull(savedOwner);
        Assertions.assertEquals(id, savedOwner.getId());
    }

    @Test
    void saveNoId() {
        Owner savedOwner = null;
        {
            Owner owner2 = Owner.builder().build();
            savedOwner = ownerService.save(owner2);
        }

        //Assertions
        Assertions.assertNotNull(savedOwner);
        Assertions.assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerService.delete(owner1);

        //Assertions
        Assertions.assertTrue(ownerService.findAll().isEmpty());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(owner1.getId());

        //Assertions
        Assertions.assertTrue(ownerService.findAll().isEmpty());
    }

    @Test
    void findByLastNameWithName() {
        Owner foundOwner = ownerService.findByLastName(owner1.getLastName());

        //Assertions
        Assertions.assertNotNull(foundOwner);
        Assertions.assertEquals(foundOwner.getId(), owner1.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner foundOwner = ownerService.findByLastName("Smith");

        //Assertions
        Assertions.assertNull(foundOwner);
    }
}