package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private Model model;

    @InjectMocks
    private OwnerController controller;

    private Set<Owner> set = null;

    private MockMvc mockMvc = null;


    @BeforeEach
    void setUp() {

        //Set<Owner>
        set = new HashSet<>();
        set.add(Owner.builder().id(1L).build());
        set.add(Owner.builder().id(2L).build());

        //MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void showListOfOwners() throws Exception {

        List<String> requestList = Arrays.asList("", "/", "/index", "/index.html");

        requestList.stream().forEach(path -> showListOfOwners("/owners" + path));
    }

    void showListOfOwners(String path) {

        //given
        {
            Mockito.when(ownerService.findAll()).thenReturn(set);
        }

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(path))

                    .andExpect(MockMvcResultMatchers.status().isOk())

                    .andExpect(MockMvcResultMatchers.view().name("owners/index"))

                    .andExpect(MockMvcResultMatchers.model().attribute("ownersList", Matchers.hasSize(set.size())));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findOwners() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))

                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.view().name("notImplemented"));

        Mockito.verifyNoInteractions(ownerService);


    }


}