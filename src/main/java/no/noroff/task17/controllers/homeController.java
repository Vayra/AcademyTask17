package no.noroff.task17.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class homeController {

    @RequestMapping("/")
    public String homePage() {
        return "nettside";
    }

    @RequestMapping("/addPerson")
    public String addPerson(){return "nettside2";}

}
