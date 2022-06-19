package com.example.lab6.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_seq")
    @SequenceGenerator(name = "book_id_seq", allocationSize = 1)
    Long id;

    @Column(name = "author")
    String author;

    @Column(name = "book_name")
    String bookName;

    @Column(name = "price")
    int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Order> orders;
}
