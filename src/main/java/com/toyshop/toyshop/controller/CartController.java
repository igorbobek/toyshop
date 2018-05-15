package com.toyshop.toyshop.controller;

import com.toyshop.toyshop.dao.CategoryDao;
import com.toyshop.toyshop.dao.ProductDao;
import com.toyshop.toyshop.dao.UserDao;
import com.toyshop.toyshop.model.Product;
import com.toyshop.toyshop.model.User;
import com.toyshop.toyshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@Secured("ROLE_USER")
public class CartController {

    @Autowired
    UserService userService;
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    UserDao userDao;
    @GetMapping("/cart")
    public ModelAndView cart(ModelAndView modelAndView){
        modelAndView.setViewName("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(auth.getName());
        modelAndView.addObject("products",user.getProducts());
        modelAndView.addObject("categories",categoryDao.findAll());
        modelAndView.addObject("cart", true);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/deleteItem")
    public ModelAndView delete(@RequestParam("productId") String idStr){
        long id;
        try{
            id = Long.parseLong(idStr);
        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
            return new ModelAndView("redirect:/");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(productDao.findById(id).isPresent()){
            if(userDao.findByName(auth.getName()).getProducts().contains(productDao.findById(id).get())){
                User user = userDao.findByName(auth.getName());
                Product product = productDao.findById(id).get();
                product.getBuyers().remove(user);
                productDao.save(product);
            }
        }
        return new ModelAndView("redirect:/cart");
    }

    @PostMapping("/addToCart")
    public ModelAndView addToCart(@RequestParam("productId") String idStr){
        long id;
        try{
            id = Long.parseLong(idStr);
        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
            return new ModelAndView("redirect:/");
        }
        if(productDao.findById(id).isPresent()){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByName(auth.getName());
            Product product = productDao.findById(id).get();
            product.getBuyers().add(user);
            productDao.save(product);
        }
        return new ModelAndView("redirect:/cart");
    }

}
