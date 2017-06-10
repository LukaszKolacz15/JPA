package com.akademiakodu.JPA.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lukasz Kolacz on 10.06.2017.
 */

@Controller
public class SecureController {
    @GetMapping("/") // to samo co @RequestMapping(value="/",method=GET)
    public String main() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "adminView";
    }

    @GetMapping("/403")
    @ResponseBody
    public String error403() {
        return "<center>Nie masz dostępu do tej strony!</center>";
    }
}
