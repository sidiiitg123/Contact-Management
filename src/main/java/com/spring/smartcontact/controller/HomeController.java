package com.spring.smartcontact.controller;

import com.spring.smartcontact.helper.Message;
import com.spring.smartcontact.model.User;
import com.spring.smartcontact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.Binding;

@Controller
public class HomeController {
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("smart", "this is smart contact manager");

        return "home";
    }

    @RequestMapping("/base")
    public String base() {

        return "base";
    }

    @RequestMapping("/signUp")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping("/do_register")
    public String registerUser(@Valid  @ModelAttribute("user") User user,BindingResult result1,
                               @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
                               Model model, HttpSession session) {
        try {
            if (!agreement) {
                throw new Exception("you have not agreed the terms and condition");
            }
            if(result1.hasErrors()){
                System.out.println("ERROR"+result1.toString());
                model.addAttribute("user",user);
                return "signUp";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

            System.out.println("Agreement" + agreement);
            System.out.println(user);
            User result = this.userRepository.save(user);
            model.addAttribute("user", result);
          session.setAttribute("message", new Message("successfully registered","alert-success" ));
            return "signUp";


        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user",user);
            Message m=new Message("Something went Wrong!!"+e.getMessage(),"alert-danger" );
            session.setAttribute("message",m);

            return "signUp";
        }


    }
}
