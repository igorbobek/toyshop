package com.toyshop.toyshop.controller;

import com.toyshop.toyshop.dao.BrandDao;
import com.toyshop.toyshop.dao.CategoryDao;
import com.toyshop.toyshop.dao.CountryDao;
import com.toyshop.toyshop.dao.ProductDao;
import com.toyshop.toyshop.model.Brand;
import com.toyshop.toyshop.model.Category;
import com.toyshop.toyshop.model.Product;
import com.toyshop.toyshop.service.ProductService;
import com.toyshop.toyshop.service.UserService;
import com.toyshop.toyshop.util.XmlUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Secured("ROLE_ADMIN")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    BrandDao brandDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    CountryDao countryDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductService productService;

    @GetMapping("/addProduct")
    public ModelAndView addProduct(ModelAndView modelAndView){
        modelAndView.setViewName("add_product");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("user", userService.findByName("admin"));
        return modelAndView;
    }

    @GetMapping("/addBrand")
    public ModelAndView addBrand(ModelAndView modelAndView){
        modelAndView.setViewName("add_brand");
        modelAndView.addObject("brand", new Brand());
        modelAndView.addObject("user", userService.findByName("admin"));
        return modelAndView;
    }

    @PostMapping("/addBrand")
    public void addBrand(@ModelAttribute("brand") @Valid Brand brand,HttpServletResponse response) throws IOException{
        if(brandDao.findByName(brand.getName()) != null){
            response.sendRedirect("/");
            return;
        }
        if(countryDao.findByName(brand.getCountry().getName()) == null){
            brandDao.save(brand);
        }else{
            brand.setCountry(countryDao.findByName(brand.getCountry().getName()));
            brandDao.save(brand);
        }
        response.sendRedirect("/");
    }

    @PostMapping("/addProduct")
    public void addProduct(@ModelAttribute("product") @Valid Product product, HttpServletResponse response) throws IOException{
        if(brandDao.findByName(product.getBrand().getName()) != null){
            if(categoryDao.findByName(product.getCategory().getName())!= null){
                product.setCategory(categoryDao.findByName(product.getCategory().getName()));
            }
            product.setDescription(product.getDescription().replaceAll("\r\n", "\n"));
            product.setBrand(brandDao.findByName(product.getBrand().getName()));
            productDao.save(product);
            response.sendRedirect("/");
        }
    }

    @PostMapping("/deleteProduct")
    public  String deletePlayer(@RequestParam("productId") String idStr, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        long id;

        try{
            id = Long.parseLong(idStr);
        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
            return "redirect:"+referer;
        }

        if(productDao.findById(id).isPresent()){
            Product product= productDao.findById(id).get();
            product.setBrand(null);
            product.setCategory(null);
            product.setBuyers(null);
            productDao.save(product);
            productDao.delete(productDao.findById(id).get());
        }
        return "redirect:"+referer;
    }

    @GetMapping("/getXml")
    public void getXml(ModelMap map, HttpServletResponse response) throws IOException {
        String xml = XmlUtil.xmlString(productDao.findAll());
        byte[] documentBody = xml.getBytes();
        InputStream is = new ByteArrayInputStream(documentBody);
        IOUtils.copy(is, response.getOutputStream());
        response.setContentType("text/xml");
        response.flushBuffer();
    }

}
