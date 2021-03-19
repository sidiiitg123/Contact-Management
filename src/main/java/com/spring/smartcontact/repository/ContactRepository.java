package com.spring.smartcontact.repository;

import com.spring.smartcontact.model.Contact;
import com.spring.smartcontact.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    //pagination
    //pageable containe current page no. and no.of entry per page
    @Query("from Contact as c where c.user.id =:userId ")
    Page<Contact> findContactByUser(@Param("userId") int userId, Pageable pageable);


    //search
    List<Contact> findByNameContainingAndUser(String name, User user);
}
