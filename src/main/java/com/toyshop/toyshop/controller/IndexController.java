package com.toyshop.toyshop.controller;

import com.toyshop.toyshop.dao.BrandDao;
import com.toyshop.toyshop.dao.CategoryDao;
import com.toyshop.toyshop.dao.ProductDao;
import com.toyshop.toyshop.dao.UserDao;
import com.toyshop.toyshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {

    @Autowired
    UserDao userDao;
    @Autowired
    BrandDao brandDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CategoryDao categoryDao;

    @GetMapping("/")
    public ModelAndView index(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("products", productDao.findAll());
        modelAndView.addObject("categories", categoryDao.findAll());
        modelAndView.addObject("user", userDao.findByName(auth.getName()));
        return modelAndView;
    }

    @GetMapping("/category/{category}")
    public ModelAndView category(@PathVariable(value = "category") String category){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("index");
        if(categoryDao.findByName(category) == null){
            return new ModelAndView("redirect:/");
        }
        modelAndView.addObject("products", productDao.findByCategoryId(categoryDao.findByName(category).getId()));
        modelAndView.addObject("categories", categoryDao.findAll());
        modelAndView.addObject("user", userDao.findByName(auth.getName()));
        return modelAndView;
    }

    @GetMapping("/product/{id}")
    public ModelAndView product(@PathVariable(value = "id") String idStr){
        Long id;
        try {
            id = Long.parseLong(idStr);
        }catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return new ModelAndView("redirect:/");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView("index");

        if(!productDao.findById(id).isPresent()){
            return new ModelAndView("redirect:/");
        }else {
            Product product = productDao.findById(id).get();
            modelAndView.addObject("product", productDao.findById(id).get());
            modelAndView.addObject("categories", categoryDao.findAll());
            modelAndView.addObject("user", userDao.findByName(auth.getName()));
        }
        return modelAndView;
    }


    @PostMapping("/")
    public void index(HttpServletResponse response) throws IOException{
        response.sendRedirect("/");
    }

}
