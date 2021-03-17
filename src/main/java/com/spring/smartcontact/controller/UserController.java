package com.spring.smartcontact.controller;


import com.spring.smartcontact.model.Contact;
import com.spring.smartcontact.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spring.smartcontact.repository.UserRepository;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private  UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    //method for adding common data to response
    @ModelAttribute
    public void addCommonData(Model model,Principal principal){
        String username=principal.getName();
        User user=userRepository.getUserByEmail(username);//username is email here
        model.addAttribute("user",user);
    }


    //dashboard home
    @RequestMapping("/index")
    public String dashboard(Model model, Principal principal){


        return "normal/user_dashboard";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){
        User user=new User();
        user.setEmail("sudhanshu457@gmail.com");
        user.setName("sid");
       userRepository.save(user);

       return "success";
    }

    //open add form handler
    @GetMapping("/add-contact")
    public String openAddContactForm(Model model){

        model.addAttribute("title","Add contact");
        model.addAttribute("contact",new Contact());
        return "normal/add_contact_form";
    }
}
