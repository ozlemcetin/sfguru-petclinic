package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private Model model;

    @InjectMocks
    private OwnerController controller;

    private MockMvc mockMvc = null;


    @BeforeEach
    void setUp() {

        //MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    void showListOfOwners() throws Exception {

        List<String> requestList = Arrays.asList("", "/", "/index", "/index.html");

        requestList.stream().forEach(path -> {
            showListOfOwnersWithPath("/owners" + path);
        });
    }

    void showListOfOwnersWithPath(String path) {

        //given
        Set<Owner> set = null;
        {
            //Set<Owner>
            set = new HashSet<>();
            set.add(Owner.builder().id(1L).build());
            set.add(Owner.builder().id(2L).build());

            //when
            Mockito.when(ownerService.findAll()).thenReturn(set);
        }

        try {

            //then
            mockMvc.perform(MockMvcRequestBuilders.get(path))

                    .andExpect(MockMvcResultMatchers.status().isOk())

                    .andExpect(MockMvcResultMatchers.view().name("owners/index"))

                    .andExpect(MockMvcResultMatchers.model().attribute("ownersList", Matchers.hasSize(set.size())));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //verify
        Mockito.verify(ownerService, Mockito.times(1)).findAll();
    }

    @Test
    void initFindForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"));

        //verify
        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    void processFindForm_ReturnEmpty() throws Exception {

        //given
        {
            //when
            Mockito.when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>());
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));

        //verify
        Mockito.verify(ownerService, Mockito.times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindForm_ReturnOne() throws Exception {

        //given
        Long ownerId = 3L;
        {
            //when
            Mockito.when(ownerService.findAllByLastNameLike(anyString()))

                    .thenReturn(Collections.singletonList(Owner.builder().id(ownerId).build()));
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())

                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/" + ownerId));

        //verify
        Mockito.verify(ownerService, Mockito.times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void processFindForm_ReturnMany() throws Exception {

        //given
        {
            //when
            Mockito.when(ownerService.findAllByLastNameLike(anyString()))

                    .thenReturn(Arrays.asList(Owner.builder().id(1L).build(), Owner.builder().id(2L).build()));
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/").param("lastName", ""))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"))

                .andExpect(MockMvcResultMatchers.model().attribute("selections", hasSize(2)));

        //verify
        Mockito.verify(ownerService, Mockito.times(1)).findAllByLastNameLike(anyString());
    }

    @Test
    void showOwner() throws Exception {

        //given
        Owner owner = null;
        Long ownerId = 1L;
        {
            owner = Owner.builder().id(ownerId).build();

            //when
            Mockito.when(ownerService.findById(anyLong())).thenReturn(owner);
        }

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/" + ownerId))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("owners/ownerDetails"))

                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))

                .andExpect(MockMvcResultMatchers.model().attribute("owner",

                        Matchers.hasProperty("id", is(ownerId))));

        //verify
        Mockito.verify(ownerService, Mockito.times(1)).findById(anyLong());
    }


    @Test
    void initCreationForm() throws Exception {

        mockMvc.perform(get("/owners/new"))

                .andExpect(status().isOk())

                .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM))

                .andExpect(model().attributeExists("owner"));

        //verify
        Mockito.verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {

        //given
        Long ownerId = 1L;
        {
            //when
            when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(ownerId).build());
        }

        //then
        mockMvc.perform(post("/owners/new"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/owners/" + ownerId))

                .andExpect(model().attributeExists("owner"));

        //verify
        verify(ownerService).save(ArgumentMatchers.any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {

        //given
        Long ownerId = 1L;
        {
            //when
            when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(ownerId).build());
        }

        //then
        mockMvc.perform(get("/owners/" + ownerId + "/edit"))

                .andExpect(status().isOk())

                .andExpect(view().name(OwnerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM))

                .andExpect(model().attributeExists("owner"));

        //verify
        verify(ownerService).findById(anyLong());
    }

    @Test
    void processUpdateOwnerForm() throws Exception {

        Long ownerId = 1L;
        {
            //when
            when(ownerService.save(ArgumentMatchers.any())).thenReturn(Owner.builder().id(ownerId).build());
        }

        //then
        mockMvc.perform(post("/owners/" + ownerId + "/edit"))

                .andExpect(status().is3xxRedirection())

                .andExpect(view().name("redirect:/owners/" + ownerId))

                .andExpect(model().attributeExists("owner"));

        //verify
        Mockito.verify(ownerService).save(ArgumentMatchers.any());
    }

}