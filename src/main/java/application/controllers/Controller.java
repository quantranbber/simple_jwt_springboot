package application.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping(value = "/")
    public String getHome() {
        return "home";
    }
}
