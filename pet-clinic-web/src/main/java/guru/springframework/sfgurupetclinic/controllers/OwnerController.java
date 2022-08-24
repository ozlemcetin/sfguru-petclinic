package guru.springframework.sfgurupetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    /*
        http://localhost:8080/owners
        http://localhost:8080/owners/
        http://localhost:8080/owners/index
        http://localhost:8080/owners/index.html
     */

    // @RequestMapping(path = {"/owners", "/owners/index", "/owners/index.html"})
    @RequestMapping(path = {"", "/", "/index", "/index.html"})
    public String showListOfVets() {
        return "owners/index";
    }
}
