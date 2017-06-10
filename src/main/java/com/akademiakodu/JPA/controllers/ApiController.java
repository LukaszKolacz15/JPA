package com.akademiakodu.JPA.controllers;

import com.akademiakodu.JPA.UserRepository;
import com.akademiakodu.JPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by Lukasz Kolacz on 05.06.2017.
 */
//                                          DO RESTA (odpalamy ApiController a następnie RestTest)
@Controller
//@RequestMapping(value = "/api")
public class ApiController {
    @Autowired
    UserRepository userRepository;

//        localhost:8080/api/user
//    Metoda do zwracania wszystkich userow:
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    //GET do uzyskiwania danych
    public ResponseEntity getAllUser(@RequestHeader("Access-Password") String key) {

        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Iterable<User> users = userRepository.findAll();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    //    Metoda do wyszukania jednego usera po jego nazwie:
    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@RequestHeader("Access-Password") String key, @PathVariable("username") String username) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return new ResponseEntity("User no exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(user.get(), HttpStatus.OK);
    }


//      AKTUALIZACJA ISTNIEJACYCH DANYCH (np zmiana hasla):
//      localhost:8080/api/user

    @RequestMapping(value = "/user", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editUser(@RequestBody User user, @RequestHeader("Access-Password") String key) {
        if (!key.equals("akademiakodujestfajna")) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        User userLocal = userRepository.findOne(user.getId());
        if (userLocal == null) {
            return new ResponseEntity("User no exist", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }


//    DODAWANIE NOWEGO USERA
    @RequestMapping(value = "/api/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody User user, @RequestHeader("Access-Password")String key){
        if(!key.equals("akademiakodujestfajna")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        if(user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Optional<User> userLocal = userRepository.findByUsername(user.getUsername());
        if(userLocal.isPresent()){
            return new ResponseEntity("Username is not free", HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);

    }

//  USUWANIE USERA Z BAZY
    @RequestMapping(value = "/api/user/{username}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteUser(
            @RequestHeader("Access-Password")String key,
            @PathVariable("username") String username) {

        if(!key.equals("akademiakodujestfajna")){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Optional<User> userLocal = userRepository.findByUsername(username);
        if(!userLocal.isPresent()) {
            return new ResponseEntity("User no exist", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(userLocal.get());
        return new ResponseEntity(HttpStatus.OK);
    }

//    SPRAWDZANIE LOGINU I HASLA
    @RequestMapping(value = "/api/checklogin/{login}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public String checkLogin(@PathVariable("login") String login, @PathVariable("password") String password){
        if((login.isEmpty() && login == null) || (password.isEmpty() || password ==  null)) {
            return "null except";
        }
        Optional<User> userLocal = userRepository.findByUsername(login);
        if(userLocal.isPresent()) {
            if(userLocal.get().getPassword().equals(password)){
                return "OK";
            }
        }
        return "BAD";
    }

    // Dynamiczne adresy URL
    @RequestMapping(value = "/oskar/**", method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpServletRequest servletRequest) throws Exception{
        String path = (String) servletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
//        String[] split = path.split("/");
        return "Pozostały path to: " + path;
    }

    // Pobieranie pojedynczych pól z formularza
    @RequestMapping(value = "/oskar/**", method = RequestMethod.POST)
    @ResponseBody
    public String test( @RequestParam(value = "login" /*required = false */) String login, @RequestParam("password") String password){
        return "Login: " + login;
    }



}
