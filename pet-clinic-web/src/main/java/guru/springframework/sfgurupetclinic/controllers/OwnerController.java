package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }



    /*
        http://localhost:8080/owners
        http://localhost:8080/owners/
        http://localhost:8080/owners/index
        http://localhost:8080/owners/index.html
     */

    // @RequestMapping(path = {"/owners", "/owners/index", "/owners/index.html"})
    @RequestMapping(path = {"", "/", "/index", "/index.html"})
    public String showListOfOwners(Model model) {

        model.addAttribute("ownersList", ownerService.findAll());

        return "owners/index";
    }
}
