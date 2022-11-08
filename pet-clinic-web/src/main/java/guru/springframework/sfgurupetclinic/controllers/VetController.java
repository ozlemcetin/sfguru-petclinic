package guru.springframework.sfgurupetclinic.controllers;

import guru.springframework.sfgurupetclinic.model.Vet;
import guru.springframework.sfgurupetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }


    @RequestMapping(path = {"/vets", "/vets/", "/vets/index", "/vets/index.html", "/vets.html"})
    public String showListOfVets(Model model) {

        model.addAttribute("vetsList", vetService.findAll());

        return "vets/index";
    }

    @GetMapping("/api/vets")
    public @ResponseBody Set<Vet> getVetsJSON() {
        return vetService.findAll();
    }
}
