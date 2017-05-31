package com.akademiakodu.JPA;

import com.akademiakodu.JPA.models.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Lukasz Kolacz on 31.05.2017.
 */

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    Optional<Ticket> findOne(int id);
    List<Ticket> findByAuthor (String author);
    List<Ticket> findByMessageLike (String prefix);
}
