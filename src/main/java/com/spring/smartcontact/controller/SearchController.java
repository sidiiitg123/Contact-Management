package com.spring.smartcontact.controller;

import com.spring.smartcontact.model.Contact;
import com.spring.smartcontact.model.User;
import com.spring.smartcontact.repository.ContactRepository;
import com.spring.smartcontact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class SearchController {

    private UserRepository userRepository;
    private ContactRepository contactRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    //search handler
    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable("query") String query,Principal principal){
        System.out.println(query);
      User user=  userRepository.getUserByEmail(principal.getName());
      List<Contact> contacts=contactRepository.findByNameContainingAndUser(query,user);

      return ResponseEntity.ok(contacts);

    }

}
