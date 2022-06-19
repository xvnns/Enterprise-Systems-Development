package com.example.lab6.service;

import com.example.lab6.entity.Book;
import com.example.lab6.entity.Order;
import com.example.lab6.entity.User;
import com.example.lab6.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.example.lab6.entity.OrderStatus.CREATED;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    public List<Order> getAllUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        return user.getOrders();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
    }

    @PreAuthorize("hasAuthority('USER')")
    public void addOrder(User user, Book book) {
        int quantity = 1;
        Order order = new Order();
        order.setStatus(CREATED);
        order.setQuantity(quantity);
        order.setDate(new Date());
        order.setUser(user);
        order.setBook(book);
        orderRepository.save(order);
        user.getOrders().add(order);
        userService.saveUser(user);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void deleteOrder(User user, Order order) {
        user.getOrders().remove(order);
        Book book = order.getBook();
        book.getOrders().remove(order);
        orderRepository.deleteById(order.getId());
        userService.saveUser(user);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void increaseQuantity(Long id) {
        Order order = getById(id);
        order.setQuantity(order.getQuantity() + 1);
        orderRepository.save(order);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void reduceQuantity(Long id) {
        Order order = getById(id);
        if (order.getQuantity() > 1) {
            order.setQuantity(order.getQuantity() - 1);
            orderRepository.save(order);
        }
        else {
            deleteOrder(order.getUser(), order);
        }
    }
}
