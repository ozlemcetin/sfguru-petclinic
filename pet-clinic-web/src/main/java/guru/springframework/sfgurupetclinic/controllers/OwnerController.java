package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Owner;
import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    public static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }


    /*
        http://localhost:8080/owners
        http://localhost:8080/owners/
        http://localhost:8080/owners/index
        http://localhost:8080/owners/index.html
     */

    // @RequestMapping(path = {"/owners","/owners/", "/owners/index", "/owners/index.html"})
    // @RequestMapping(path = {"", "/", "/index", "/index.html"})
    /* public String showListOfOwners(Model model) {

        model.addAttribute("ownersList", ownerService.findAll());
        return "owners/index";
    }*/

    @RequestMapping(path = {"/find", "/find/"})
    public String initFindForm(Model model) {

        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    /*
          allow parameterless GET request for /owners to return all records
     */
    @GetMapping
    public String processFindForm(Model model, Owner owner, BindingResult bindingResult) {

        // empty string signifies broadest possible search
        if (owner.getLastName() == null) owner.setLastName("");

        // find owners by last name
        List<Owner> owners = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");

        if (owners.isEmpty()) {
            // no owners found
            bindingResult.rejectValue("lastName", "notFound", "no owners has been found");
            return "owners/findOwners";

        } else if (owners.size() == 1) {
            // 1 owner found
            owner = owners.get(0);
            return "redirect:/owners/" + owner.getId();

        } else {
            // multiple owners found
            model.addAttribute("selections", owners);
            return "owners/ownersList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {

        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        {
            mav.addObject(ownerService.findById(ownerId));
        }
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {

        model.addAttribute("owner", Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }

        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateOwnerForm(@PathVariable("ownerId") Long ownerId, Model model) {

        model.addAttribute("owner", ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateOwnerForm(@Valid Owner owner, BindingResult bindingResult, @PathVariable Long ownerId) {

        if (bindingResult.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }

        owner.setId(ownerId);
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }
}