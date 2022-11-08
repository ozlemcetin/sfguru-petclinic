package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Pet;
import guru.springframework.sfgurupetclinic.model.Visit;
import guru.springframework.sfgurupetclinic.services.PetService;
import guru.springframework.sfgurupetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class VisitController {

    public static final String PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    public static final String REDIRECT_OWNERS_OWNERS_ID = "redirect:/owners/{ownerId}";


    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    /**
     * Called before each and every @RequestMapping annotated method.
     * 2 goals:
     * - Make sure we always have fresh data
     * - Since we do not use the session scope, make sure that Pet object always has an id
     * (Even though id is not part of the form fields)
     *
     * @param petId
     * @return Pet
     */
    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> modelMap) {

        Pet pet = petService.findById(petId);
        modelMap.put("pet", pet);

        Visit visit = new Visit();
        visit.setPet(pet);
        pet.getVisits().add(visit);
        return visit;
    }

    // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Map<String, Object> modelMap) {
        return PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@Valid Visit visit, BindingResult result) {

        if (result.hasErrors()) {
            return PETS_CREATE_OR_UPDATE_VISIT_FORM;
        }

        visitService.save(visit);
        // return "redirect:/owners/{ownerId}";
        return REDIRECT_OWNERS_OWNERS_ID;
    }
}
