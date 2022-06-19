package com.example.lab6.service;

import com.example.lab6.entity.Book;
import com.example.lab6.entity.ShoppingCart;
import com.example.lab6.entity.User;
import com.example.lab6.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public Book getById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void changeName(Long bookId, String name) {
        Book book = getById(bookId);
        book.setBookName(name);
        bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void changeAuthor(Long bookId, String author) {
        Book book = getById(bookId);
        book.setAuthor(author);
        bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void changePrice(Long bookId, int price) {
        Book book = getById(bookId);
        book.setPrice(price);
        bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void addBook(String name, String author, int price) {
        Book book = new Book();
        book.setBookName(name);
        book.setAuthor(author);
        book.setPrice(price);
        bookRepository.save(book);
    }

    @PreAuthorize("hasAuthority('USER')")
    public void addToCart(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        ShoppingCart shoppingCart = new ShoppingCart(user);
        Book book = getById(id);
        if (!shoppingCart.containBook(book)) {
            orderService.addOrder(user, book);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    public void deleteFromCart(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        ShoppingCart shoppingCart = new ShoppingCart(user);
        Book book = getById(id);
        if (shoppingCart.containBook(book)) {
            orderService.deleteOrder(user, shoppingCart.getOrder(book));
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
