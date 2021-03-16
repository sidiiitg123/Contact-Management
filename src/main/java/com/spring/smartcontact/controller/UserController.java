package com.spring.smartcontact.controller;


import com.spring.smartcontact.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spring.smartcontact.repository.UserRepository;

@Controller
public class UserController {

    private  UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
