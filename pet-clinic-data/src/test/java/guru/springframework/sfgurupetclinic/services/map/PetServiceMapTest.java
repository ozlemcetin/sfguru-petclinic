package guru.springframework.sfgurupetclinic.services.map;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceMapTest {

    private PetServiceMap petService;

    private Pet setupPet;

    @BeforeEach
    void setUp() {

        //As they are hash map implementations
        petService = new PetServiceMap(new VisitServiceMap());

        //Put one object into the map
        setupPet = Pet.builder().id(1L).build();
        petService.save(setupPet);
    }

    @Test
    void findAll() {
        Set<Pet> set = petService.findAll();

        //Assertions
        Assertions.assertEquals(set.size(), 1);
        Assertions.assertTrue(set.contains(setupPet));
    }

    @Test
    void findById_ExistingId() {
        Pet foundPet = petService.findById(setupPet.getId());

        //Assertions
        Assertions.assertNotNull(foundPet);
        Assertions.assertEquals(foundPet.getId(), setupPet.getId());
    }

    @Test
    void findById_NotExistingId() {
        Pet pet = petService.findById(5L);

        //Assertions
        assertNull(pet);
    }

    @Test
    void findById_NullId() {
        Pet pet = petService.findById(null);

        //Assertions
        assertNull(pet);
    }

    @Test
    void saveWithId() {
        Long id = 2L;
        Pet savedPet = null;
        {
            Pet pet2 = Pet.builder().id(id).build();
            savedPet = petService.save(pet2);
        }

        //Assertions
        Assertions.assertNotNull(savedPet);
        Assertions.assertEquals(id, savedPet.getId());

        assertEquals(2, petService.findAll().size());
    }


    @Test
    void saveNoId() {
        Pet savedPet = null;
        {
            Pet pet2 = Pet.builder().build();
            savedPet = petService.save(pet2);
        }

        //Assertions
        assertNotNull(savedPet);
        assertNotNull(savedPet.getId());

        assertEquals(2, petService.findAll().size());
    }

    @Test
    void saveDuplicateId() {
        Long id = 1L;
        Pet savedPet = null;
        {
            Pet pet2 = Pet.builder().id(id).build();
            savedPet = petService.save(pet2);
        }

        //Assertions
        Assertions.assertNotNull(savedPet);
        Assertions.assertEquals(id, savedPet.getId());

        assertEquals(1, petService.findAll().size());
    }


    @Test
    void deletePet() {
        petService.delete(setupPet);

        //Assertions
        Assertions.assertTrue(petService.findAll().isEmpty());
    }

    @Test
    void deletePet_WithWrongId() {
        Pet pet = Pet.builder().id(5L).build();
        petService.delete(pet);

        //Assertions
        assertEquals(1, petService.findAll().size());
    }

    @Test
    void deletePet_WithNullId() {
        Pet pet = Pet.builder().build();
        petService.delete(pet);

        //Assertions
        assertEquals(1, petService.findAll().size());
    }


    @Test
    void deleteById_CorrectId() {
        petService.deleteById(setupPet.getId());

        //Assertions
        assertEquals(0, petService.findAll().size());
    }

    @Test
    void deleteById_WrongId() {
        petService.deleteById(5L);

        //Assertions
        assertEquals(1, petService.findAll().size());
    }

    @Test
    void deleteById_NullId() {
        petService.deleteById(null);

        assertEquals(1, petService.findAll().size());
    }

}