package com.example.lab6.controller;

import com.example.lab6.entity.Book;
import com.example.lab6.entity.ShoppingCart;
import com.example.lab6.entity.User;
import com.example.lab6.service.BookService;
import com.example.lab6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    UserService userService;

    @GetMapping("/get/all")
    public String getBookList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        List<Book> bookList = bookService.getAllBooks();
        ShoppingCart shoppingCart = new ShoppingCart(user);
        model.addAttribute("books", bookList);
        model.addAttribute("cart", shoppingCart);
        model.addAttribute("currentUser", user);
        return "books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBookById(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/book/get/all";
    }

    @GetMapping("/change/name/{id}")
    public String changeBookNameGet(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("address", "/book/change/name/");
        return "change_book";
    }

    @PostMapping("/change/name/{id}")
    public String changeBookName(@PathVariable("id") Long id, @RequestParam(value = "message-text") String name) {
        bookService.changeName(id, name);
        return "redirect:/book/get/all";
    }

    @GetMapping("/change/author/{id}")
    public String changeAuthorGet(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("address", "/book/change/author/");
        return "change_book";
    }

    @PostMapping("/change/author/{id}")
    public String changeAuthor(@PathVariable("id") Long id, @RequestParam(value = "message-text") String author) {
        bookService.changeAuthor(id, author);
        return "redirect:/book/get/all";
    }

    @GetMapping("/change/price/{id}")
    public String changePriceGet(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        model.addAttribute("book", bookService.getById(id));
        model.addAttribute("address", "/book/change/price/");
        return "change_book";
    }

    @PostMapping("/change/price/{id}")
    public String changePrice(@PathVariable("id") Long id, @RequestParam(value = "message-text") int price) {
        bookService.changePrice(id, price);
        return "redirect:/book/get/all";
    }

    @GetMapping("/add")
    public String addBookGet(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());
        model.addAttribute("currentUser", user);
        return "add_book";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam(value = "book-name") String name,
                          @RequestParam(value = "author") String author,
                          @RequestParam(value = "price") int price) {
        bookService.addBook(name, author, price);
        return "redirect:/book/get/all";
    }

    @GetMapping("/add-to-shopping-cart/{id}")
    public String addBookToCart(@PathVariable("id") Long id) {
        bookService.addToCart(id);
        return "redirect:/book/get/all";
    }

    @GetMapping("/delete-from-shopping-cart/{id}")
    public String deleteBookFromCart(@PathVariable("id") Long id) {
        bookService.deleteFromCart(id);
        return "redirect:/book/get/all";
    }
}
