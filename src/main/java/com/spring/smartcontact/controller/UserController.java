package com.spring.smartcontact.controller;


import com.spring.smartcontact.model.Contact;
import com.spring.smartcontact.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.spring.smartcontact.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @PostMapping("/process-contact")
    public String processContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,Principal principal){
        try{
            String username=principal.getName();
            User user=userRepository.getUserByEmail(username);

            //processing and uploading file..
            if(file.isEmpty()){
                System.out.println("file is empty");
            }else{
                //upload file to folder
                contact.setImage(file.getOriginalFilename());
                File saveFile=new ClassPathResource("static/img").getFile();
                Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            userRepository.save(user);
            System.out.println(contact);
        }catch (Exception e){
            System.out.println("ERROR" + e.getMessage());
            e.printStackTrace();
        }

        return "normal/add_contact_form";
    }
}
