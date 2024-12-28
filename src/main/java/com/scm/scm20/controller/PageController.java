package com.scm.scm20.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    private String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public  String home(Model model) {
        System.out.println("Home Page Handler");
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code with Yogesh");
        model.addAttribute("gitRepo", "https://github.com/yogesh78026");
        return "home";
    }

    // about page
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About Page Loading");
        return "about";
    }

    // Services Page
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services Page Loading");
        return "services";
    }
    
    // contact Page
    @GetMapping("/contact")
    public String contact() {
        System.out.println("Contact Page Loading");
        return "contact";
    }
    
    // login page
    @GetMapping("/login")
    public String login() {
        System.out.println("Login Page Loading");
        return "login";
    }
  
    // login page
    @GetMapping("/signup")
    public String signup(Model model) {
        // we can create an custom data also
        UserForm userForm = new UserForm();
        // userForm.setAbout("This is about Page");
        model.addAttribute("userForm", userForm);
        return "signup";
    }


    // Processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("processing Register");
        // fetch form data
        // userForm - class

        // validate data 
        if(rBindingResult.hasErrors()) {
            return "signup";
        }

        // save to database
        // userService 
        // from userform we created a user by extracting the data.
        // User user = User.builder().name(userForm.getName())
        //                           .email(userForm.getEmail())
        //                           .password(userForm.getPassword())
        //                           .about(userForm.getAbout())
        //                           .phoneNumber(userForm.getPhoneNumber())
        //                           .profilePic("\"D:\\mine\\RealmePhotos\\IMG20230405131850.jpg\"")
        //                           .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("\\\"D:\\\\mine\\\\RealmePhotos\\\\IMG20230405131850.jpg\\\"");
        
        User savedUser = userService.saveUser(user);
        System.out.println("user saved :");

        // message registration successful
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        session.setAttribute("message", message);

        // redirection to login page
        return "redirect:/signup";
    }

}
