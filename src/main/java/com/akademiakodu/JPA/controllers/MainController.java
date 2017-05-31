package com.akademiakodu.JPA.controllers;

import com.akademiakodu.JPA.TicketRepository;
import com.akademiakodu.JPA.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Lukasz Kolacz on 31.05.2017.
 */

@Controller
public class MainController {

    @Autowired
    TicketRepository ticketRepository;

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

    }
}
