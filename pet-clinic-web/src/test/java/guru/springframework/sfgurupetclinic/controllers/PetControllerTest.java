package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    private final Long ownerId = 1L;
    @Mock
    private PetService petService;
    @Mock
    private PetTypeService petTypeService;
    @Mock
    private OwnerService ownerService;
    @InjectMocks
    private PetController controller;
    private MockMvc mockMvc;
    private Set<PetType> petTypes;
    private Owner owner;

    @BeforeEach
    void setUp() {

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //petTypeSet
        {
            petTypes = new HashSet<>();
            petTypes.add(PetType.builder().id(1L).name("Dog").build());
            petTypes.add(PetType.builder().id(2L).name("Cat").build());
        }

        //owner
        owner = Owner.builder().id(ownerId).build();
    }

    @Test
    void initCreationForm() throws Exception {

        //when
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        //then
        mockMvc.perform(get("/owners/" + ownerId + "/pets/new"))

                .andExpect(status().isOk())

                .andExpect(model().attributeExists("petTypes"))

                .andExpect(model().attributeExists("owner"))

                .andExpect(model().attributeExists("pet"))

                .andExpect(view().name(PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processCreationForm() throws Exception {

        //when
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        //then
        mockMvc.perform(post("/owners/" + ownerId + "/pets/new"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/owners/" + ownerId));

        //verify
        verify(petService, times(1)).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {

        //given
        Long petId = 2L;
        {
            //when
            when(petTypeService.findAll()).thenReturn(petTypes);
            when(ownerService.findById(anyLong())).thenReturn(owner);
            when(petService.findById(anyLong())).thenReturn(Pet.builder().id(petId).build());
        }

        //then
        mockMvc.perform(get("/owners/" + ownerId + "/pets/" + petId + "/edit"))

                .andExpect(status().isOk())

                .andExpect(model().attributeExists("petTypes"))

                .andExpect(model().attributeExists("owner"))

                .andExpect(model().attributeExists("pet"))

                .andExpect(view().name(PetController.VIEWS_PETS_CREATE_OR_UPDATE_FORM));
    }

    @Test
    void processUpdateForm() throws Exception {
        //given
        Long petId = 2L;
        {
            //when
            when(petTypeService.findAll()).thenReturn(petTypes);
            when(ownerService.findById(anyLong())).thenReturn(owner);
        }

        //then
        mockMvc.perform(post("/owners/" + ownerId + "/pets/" + petId + "/edit"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/owners/" + ownerId));

        //verify
        verify(petService, times(1)).save(any());
    }
}