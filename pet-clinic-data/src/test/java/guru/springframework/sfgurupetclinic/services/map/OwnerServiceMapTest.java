package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerService;
    private Owner setupOwner;

    @BeforeEach
    void setUp() {

        //As they are hash map implementations
        PetService petService = new PetServiceMap(new VisitServiceMap());
        ownerService = new OwnerServiceMap(petService);

        //Put one owner object into the map
        setupOwner = Owner.builder().firstName("Michael").lastName("Weston").address("123 Brickerel").city("Miami").telephone("1231231234").pets(new HashSet<>()).build();
        ownerService.save(setupOwner);
    }

    @Test
    void findAll() {
        Set<Owner> set = ownerService.findAll();

        //Assertions
        Assertions.assertEquals(set.size(), 1);
        Assertions.assertTrue(set.contains(setupOwner));
    }

    @Test
    void findById() {
        Owner foundOwner = ownerService.findById(setupOwner.getId());

        //Assertions
        Assertions.assertNotNull(foundOwner);
        Assertions.assertEquals(foundOwner.getId(), setupOwner.getId());
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
        ownerService.delete(setupOwner);

        //Assertions
        Assertions.assertTrue(ownerService.findAll().isEmpty());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(setupOwner.getId());

        //Assertions
        Assertions.assertTrue(ownerService.findAll().isEmpty());
    }

    @Test
    void findByLastNameWithName() {
        Owner foundOwner = ownerService.findByLastName(setupOwner.getLastName());

        //Assertions
        Assertions.assertNotNull(foundOwner);
        Assertions.assertEquals(foundOwner.getId(), setupOwner.getId());
    }

    @Test
    void findByLastNameNotFound() {
        Owner foundOwner = ownerService.findByLastName("Smith");

        //Assertions
        Assertions.assertNull(foundOwner);
    }
}