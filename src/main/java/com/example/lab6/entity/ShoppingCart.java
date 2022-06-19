package com.example.lab6.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.lab6.entity.OrderStatus.CREATED;

@Data
public class ShoppingCart {
    private int totalCost;

    private List<Order> orders;

    public ShoppingCart(User user) {
        this.orders = new ArrayList<>();
        for (Order order: user.getOrders()) {
            if (order.status == CREATED) {
                this.orders.add(order);
            }
        }
        this.calculateTotalCost();
    }

    public void calculateTotalCost() {
        this.totalCost = 0;
        for (Order order:orders) {
            this.totalCost += order.getBook().getPrice() * order.getQuantity();
        }
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        this.totalCost += order.getBook().getPrice() * order.getQuantity();
    }

    public boolean containBook(Book book) {
        for (Order order : this.orders) {
            if (Objects.equals(order.getBook().id, book.id)) {
                return true;
            }
        }
        return false;
    }

    public Order getOrder(Book book) {
        for (Order order : this.orders) {
            if (Objects.equals(order.getBook().id, book.id)) {
                return order;
            }
        }
        return new Order();
    }
}
