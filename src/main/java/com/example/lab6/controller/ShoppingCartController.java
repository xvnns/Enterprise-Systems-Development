package com.example.lab6.controller;

import com.example.lab6.entity.ShoppingCart;
import com.example.lab6.entity.User;
import com.example.lab6.service.ShoppingCartService;
import com.example.lab6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String getShoppingCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        ShoppingCart shoppingCart = new ShoppingCart(user);
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("currentUser", user);
        return "shopping_cart";
    }

    @GetMapping("/make-payment")
    public String makePayment() {
        shoppingCartService.makePayment();
        return "redirect:/shopping-cart/";
    }
}
