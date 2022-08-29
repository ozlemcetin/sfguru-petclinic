package guru.springframework.sfgurupetclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(path = {"", "/", "index", "index.html"})
    public String showIndexPage() {

        return "index";
    }

    @RequestMapping(path = {"/oups"})
    public String showErrorPage() {

        return "notImplemented";
    }
}
