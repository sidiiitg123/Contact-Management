package com.spring.smartcontact.controller;


import com.spring.smartcontact.helper.Message;
import com.spring.smartcontact.model.Contact;
import com.spring.smartcontact.model.User;
import com.spring.smartcontact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.spring.smartcontact.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private ContactRepository contactRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //method for adding common data to response
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.getUserByEmail(username);//username is email here
        model.addAttribute("user", user);
    }


    //dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal) {


        return "normal/user_dashboard";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        User user = new User();
        user.setEmail("sudhanshu457@gmail.com");
        user.setName("sid");
        userRepository.save(user);

        return "success";
    }

    //open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model) {

        model.addAttribute("title", "Add contact");
        model.addAttribute("contact", new Contact());
        return "normal/add_contact_form";
    }

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {
        try {
            String username = principal.getName();
            User user = userRepository.getUserByEmail(username);

            //processing and uploading file..
            if (file.isEmpty()) {
                System.out.println("file is empty");
            } else {
                //upload file to folder
                contact.setImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            userRepository.save(user);
            session.setAttribute("message", new Message("Contact is inserted successfully!!", "alert-success"));
            System.out.println(contact);
        } catch (Exception e) {
            session.setAttribute("message", new Message("error in inserting contact!!", "alert-danger"));
            System.out.println("ERROR" + e.getMessage());
            e.printStackTrace();
        }

        return "normal/add_contact_form";
    }

    //show contact handler
    //per-page 5[n]
    //current page=0[page]
    @GetMapping("/show-contacts/{page}")
    public String showContacts(@PathVariable("page") Integer page, Model model,Principal principal) {
        model.addAttribute("title", "user contacts");

        String username = principal.getName();
        User user = userRepository.getUserByEmail(username);
        //current page and no. of entries
        Pageable pageable=PageRequest.of(page,3);
        Page<Contact> contacts= contactRepository.findContactByUser(user.getId(),pageable);

        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",contacts.getTotalPages());

        return "normal/show_contacts";
    }


}
