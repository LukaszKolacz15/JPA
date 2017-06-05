package com.akademiakodu.JPA;

import com.akademiakodu.JPA.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lukasz Kolacz on 03.06.2017.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    List<User> findUserByRole(String role);
    List<User> findByDatetimeBetween(Date date1, Date date2);
    List<User> findByUsernameContainingAndIdGreaterThan(String reg, int id);

//    PAGINACJA:
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUsername(String username);
}
