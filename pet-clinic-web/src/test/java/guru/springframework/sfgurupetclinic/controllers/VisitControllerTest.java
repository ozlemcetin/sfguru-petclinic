package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.PetType;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    private final UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    private final Map<String, String> uriVariables = new HashMap<>();
    @Mock
    private PetService petService;
    @Mock
    private VisitService visitService;
    @InjectMocks
    private VisitController visitController;
    private MockMvc mockMvc;
    private URI visitsUri;

    @BeforeEach
    void setUp() {

        //given
        Long petId = 1L;
        Long ownerId = 1L;
        {
            //when
            Owner owner = Owner.builder()

                    .id(ownerId).lastName("Doe").firstName("Joe").build();

            PetType petType = PetType.builder()

                    .name("Dog").build();

            Pet pet = Pet.builder()

                    .id(petId).birthDate(LocalDate.of(2018, 11, 13))

                    .name("Cutie").visits(new HashSet<>())

                    .owner(owner).petType(petType).build();

            //when
            when(petService.findById(anyLong())).thenReturn(pet);
        }

        //uriVariables
        uriVariables.clear();
        {
            uriVariables.put("ownerId", ownerId.toString());
            uriVariables.put("petId", petId.toString());
        }

        //visitsUri
        visitsUri = visitsUriTemplate.expand(uriVariables);

        //mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
    }

    @Test
    void initNewVisitForm() throws Exception {

        mockMvc.perform(get(visitsUri))

                .andExpect(status().isOk())

                .andExpect(view().name(VisitController.PETS_CREATE_OR_UPDATE_VISIT_FORM))

                .andExpect(model().attributeExists("pet"))

                .andExpect(model().attributeExists("visit"));
    }

    @Test
    void processNewVisitForm() throws Exception {


        mockMvc.perform(post(visitsUri)

                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)

                        .param("date", "2018-11-11")

                        .param("description", "yet another visit"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name(VisitController.REDIRECT_OWNERS_OWNERS_ID))

                .andExpect(model().attributeExists("pet"))

                .andExpect(model().attributeExists("visit"));
    }
}