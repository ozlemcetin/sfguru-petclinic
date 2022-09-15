package guru.springframework.sfgurupetclinic.services.jpa;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    private static final String LAST_NAME = "Smith";
    private static final Long ID = 1L;
    private Owner setupOwner;

    @Mock
    private OwnerRepository ownerRepository;
    @InjectMocks
    private OwnerServiceJpa ownerService;


    @BeforeEach
    void setUp() {
        setupOwner = Owner.builder().id(ID).lastName(LAST_NAME).build();
    }


    @Test
    void findByLastName() {

        //given
        {
            Mockito.when(ownerRepository.findByLastName(ArgumentMatchers.anyString())).thenReturn(setupOwner);
        }

        //when
        Owner owner = ownerService.findByLastName(LAST_NAME);
        assertNotNull(owner);
        assertEquals(ID, owner.getId());
        assertEquals(LAST_NAME, owner.getLastName());

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).findByLastName(ArgumentMatchers.anyString());
        }
    }

    @Test
    void findAll() {

        //given
        Set<Owner> returnOwnersSet = null;
        {
            returnOwnersSet = new HashSet<>();
            returnOwnersSet.add(Owner.builder().id(1L).build());
            returnOwnersSet.add(Owner.builder().id(2L).build());

            Mockito.when(ownerRepository.findAll()).thenReturn(returnOwnersSet);
        }

        //when
        Set<Owner> owners = ownerService.findAll();
        assertNotNull(owners);
        assertEquals(returnOwnersSet.size(), owners.size());

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).findAll();
        }
    }

    @Test
    void findById() {

        //given
        {
            Mockito.when(ownerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(setupOwner));
        }

        //when
        Owner owner = ownerService.findById(ID);
        assertNotNull(owner);
        assertEquals(ID, owner.getId());

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }


    @Test
    void findById_NotFound() {

        //given
        {
            Mockito.when(ownerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        }

        //when
        Owner owner = ownerService.findById(ID);
        assertNull(owner);

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        }
    }


    @Test
    void save() {

        //given
        {
            Mockito.when(ownerRepository.save(ArgumentMatchers.any())).thenReturn(setupOwner);
        }

        //when
        Owner owner = ownerService.save(setupOwner);
        assertNotNull(owner);
        assertEquals(owner, owner);

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).save(ArgumentMatchers.any());
        }
    }

    @Test
    void delete() {

        //when
        ownerService.delete(setupOwner);

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).delete(ArgumentMatchers.any());
        }
    }

    @Test
    void deleteById() {

        //when
        ownerService.deleteById(ID);

        //then
        {
            Mockito.verify(ownerRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        }
    }
}