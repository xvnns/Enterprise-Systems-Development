package com.example.lab6.service;

import com.example.lab6.entity.Order;
import com.example.lab6.entity.OrderStatus;
import com.example.lab6.entity.ShoppingCart;
import com.example.lab6.entity.User;
import com.example.lab6.repository.OrderRepository;
import com.example.lab6.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @PreAuthorize("hasAuthority('USER')")
    public void makePayment() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(authentication.getName());
        ShoppingCart shoppingCart = new ShoppingCart(user);
        for (Order order : shoppingCart.getOrders()) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);

        }
    }
}
