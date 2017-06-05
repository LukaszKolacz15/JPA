package com.akademiakodu.JPA.controllers;

import com.akademiakodu.JPA.UserRepository;
import com.akademiakodu.JPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Lukasz Kolacz on 05.06.2017.
 */
//                                          DO RESTA (odpalamy ApiController a następnie RestTest)
@Controller
@RequestMapping(value = "/api")
public class ApiController {
    @Autowired
    UserRepository userRepository;

//    localhost:8080/api/user
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) //GET do uzyskiwania danych
    public ResponseEntity getAllUser(@RequestHeader("Access-Password")String key){

        if(!key.equals("akademiakodujestfajna")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Iterable<User> users = userRepository.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

}
