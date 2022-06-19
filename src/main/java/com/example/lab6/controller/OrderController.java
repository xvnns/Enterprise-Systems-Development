package com.example.lab6.controller;

import com.example.lab6.entity.Order;
import com.example.lab6.entity.User;
import com.example.lab6.service.OrderService;
import com.example.lab6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    UserService userService;

    @GetMapping("/get/all")
    public String getOrderList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        List<Order> orderList = orderService.getAllUserOrders();
        model.addAttribute("orders", orderList);
        return "orders";
    }

    @GetMapping("/quantity/increase/{id}")
    public String increaseQuantity(@PathVariable("id") Long id) {
        orderService.increaseQuantity(id);
        return "redirect:/shopping-cart/";
    }

    @GetMapping("/quantity/reduce/{id}")
    public String reduceQuantity(@PathVariable("id") Long id) {
        orderService.reduceQuantity(id);
        return "redirect:/shopping-cart/";
    }
}
