package com.toyshop.toyshop.controller;

import com.toyshop.toyshop.model.User;
import com.toyshop.toyshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SignupController {

    @Autowired
    UserService userService;

    @Secured("ROLE_ANONYMOUS")
    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @Secured("ROLE_ANONYMOUS")
    @PostMapping("/signup")
    public ModelAndView signup(@ModelAttribute("user") User userForm, @RequestParam("matchingPassword") String matchPass, ModelAndView modelAndView){

        if(userForm.getName().equals("") || userForm.getEmail().equals("") || userForm.getPassword().equals("")){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "Вводимые поля не должны быть пустыми!");
            return modelAndView;
        }

        if (!userForm.getPassword().equals(matchPass)){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", "Оба введенных пароля должны быть идентичны!");
            return modelAndView;
        }
        try{
            userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
            userService.save(userForm);
            modelAndView = new ModelAndView(new RedirectView("/"));
            return modelAndView;
        }catch (Exception e){
            modelAndView.setViewName("signup");
            modelAndView.addObject("error", e.getMessage());
            System.err.println(e.getMessage());
        }

        return modelAndView;
    }
}
