package com.akademiakodu.JPA.controllers;

import com.akademiakodu.JPA.models.UserInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Lukasz Kolacz on 10.06.2017.
 */

@Controller
public class SecureController {

    @Autowired
    UserInfoBean userInfo;

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
//    -------------------------------- SESIONS -------------------------------

    @GetMapping("/testbean/{message}")
    @ResponseBody
    public String setUserInfo(@PathVariable("message")String message){
        userInfo.setMessage(message);
        return "Ustawilem tekst dla ciebie: " + message;
    }

    @GetMapping("/testbean")
    @ResponseBody
    public String getUserInfo(){
        return "Twój tekst to: " + userInfo.getMessage();
    }

}
