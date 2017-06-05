package com.akademiakodu.JPA.controllers;

import com.akademiakodu.JPA.MailService;
import com.akademiakodu.JPA.TicketRepository;
import com.akademiakodu.JPA.UserRepository;
import com.akademiakodu.JPA.models.Ticket;
import com.akademiakodu.JPA.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;


import javax.naming.Context;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Lukasz Kolacz on 31.05.2017.
 */

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

    //-------------------------------- 03.06.2017 -----------------------------------------
    @Autowired
    TicketRepository ticketRepository;
//------------------------------------- mail ----------------------------------------------

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    MailService mailService;

//    --------------------------------------------------------------------------------------
    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    @ResponseBody
    public String home(@PathVariable("ticketId") int id) {

//        Ticket ticket = ticketRepository.findOne(3);
//        ---------------------------------------------
//        Ticket ticket = ticketRepository.findOne(id);
//        ---------------------------------------------
//        Optional<Ticket> ticket = ticketRepository.findOne(id);
//        if (ticket.isPresent()) {
//            return "Odczytałem z bazy: " + ticket.get().getMessage();
//        }
//        return "To id nie istnieje";
////        Zamiennie z ifem można zastosowac lambde:
////        TODO dopisac lambde z nagrania:
////        return ticket.map()
//        ---------------------------------------------
////        Ze strumieniem:
//        List<Ticket> tickets = ticketRepository.findByAuthor("Lukasz Kolacz");
////        return tickets.stream().map(s -> s.getMessage()).collect(Collectors.joining(" , ", "Tickety: ", ""));
//
////        Bez strumienia:
//        String massages = "Tickety Lukasz Kolacza: ";
//        for (Ticket ticket : tickets) {
//            massages += ticket.getMessage() + " , ";
//        }
//        return massages;
//        ---------------------------------------------

        List<Ticket> tickets = ticketRepository.findByMessageLike("Wiadomość%");
        return tickets.stream().map(s -> s.getMessage()).collect(Collectors.joining(" , ", "Tickety: ", ""));

//        ------------------ 03.06.2017 ---------------------------------------------------

    }

    //    @RequestMapping(value="/user",method = RequestMethod.GET)
//    @ResponseBody
//    public String user(){
//        List<User> users = userRepository.findUserByRole("ADMIN");
//        return users.stream().map(s->s.getUsername()).collect(Collectors.joining("", "Role: ", ""));
//    }
//    ------------------------------------------------------------------------------------------
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    @ResponseBody
//    public String user(){
//        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Date date1 = null;
//        Date date2 = null;
//        try {
//            date1 = formater.parse("2017-04-12 16:32:06");
//            date2 = formater.parse("2017-06-13 00:00:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        List<User> users = userRepository.findByDatetimeBetween(date1, date2);
//        return users.stream().map(s -> s.getUsername()).collect(
//                Collectors.joining(" , ", "Role: ", ""));
//
//    }
//    ----------------------------------------------------------------------------------------------
//    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    @ResponseBody
//    public String user(){
//
//        List<User> users = userRepository.findByUsernameContainingAndIdGreaterThan("os", 3);
//        return users.stream().map(s -> s.getUsername()).collect(
//                Collectors.joining(" , ", "Role: ", ""));
//
//    }
//    --------------------------------- paginacja ---------------------------------------------------------------
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public String user() {

        Page<User> currentPage = userRepository.findAll(new PageRequest(0, 4));
        StringBuilder builder = new StringBuilder();
        for (User user : currentPage.getContent()) {
            builder.append("Username: " + user.getUsername() + "<br>");
        }

        builder.append("<br> Ilość stron: " + currentPage.getTotalPages());
        builder.append("<br> Czy zawiera następną stronę? " + currentPage.hasNext());
        builder.append("<br> Czy zawiera poprzednia stronę? " + currentPage.hasPrevious());

        currentPage=userRepository.findAll(currentPage.nextPageable());

        builder.append("<br><br><br><br>");

        for (User user : currentPage.getContent()) {
            builder.append("Username: " + user.getUsername() + "<br>");
        }

        return builder.toString();

    }
//    ------------------------------------ mailService ---------------------------------------

    @RequestMapping(value = "/mail/{cash}", method = RequestMethod.GET)
    @ResponseBody
    public String email(@PathVariable("cash") int cash){
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();
        context.setVariable("welcome", "witaj Janie Kowalski!");
        context.setVariable("message", "wisisz nam już " + cash + "zł");

        String bodyHTML = templateEngine.process("emailone",context);

        mailService.sendEmail("lukasz.kolacz@artatech.pl", bodyHTML, "Wysłane z wykładu");
        return "Wysłano maila!";
    }

//    @RequestMapping(value = "/mail/{cash}", method = RequestMethod.GET)
//    @ResponseBody
//    public String email(@PathVariable("cash") int cash) {
//        Context context = new Context();
//        context.setVariable("welcome", "Witaj Janie Kowalski!");
//        context.setVariable("message", "Wisisz nam już " + cash + " zł");
//
//        String bodyHtml = templateEngine.process("emailone", context);
//
//        mailSerivce.sendEmail("lukaszbilski1@gmail.com", bodyHtml, "Wysłane z wykładu");
//        return "Wysłano maila!";
//    }

}
