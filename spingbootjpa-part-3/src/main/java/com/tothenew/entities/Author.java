package com.tothenew.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Embedded
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "author_subject",
            joinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id", referencedColumnName = "id")})
    private List<Subject> subjects = new ArrayList<>();


    /* OneToOne Bidirectional Mapping (Author <==> Book) */
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL)
    private Book book;

    public Book getBook() {
        return book;
    }

    public Author setBook(Book book) {
        this.book = book;
        return this;
    }



    /* OneToMany Unidirectional Mapping (Author --> Book) */
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "author_id") // Hibernate creates child table atomically with Foreign Key Constraint.
//    private List<Book> books = new ArrayList<>();


    /* OneToMany Bidirectional Mapping (Author <==> Book) */
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Book> books = new ArrayList<>();



    /* ManyToMany Bidirectional Mapping (Author <==> Book) */

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "author_book",
//            joinColumns = {@JoinColumn(name = "author_id")},
//            inverseJoinColumns = {@JoinColumn(name = "book_id")})
//    private List<Book> books = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public Author setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Author setAddress(Address address) {
        this.address = address;
        return this;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public Author setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        return this;
    }

//    public List<Book> getBooks() {
//        return books;
//    }
//
//    public Author setBooks(List<Book> books) {
//        this.books = books;
//        return this;
//    }


    /* OneToMany Bidirectional Utilities */
//    public void addBook(Book book) {
//        books.add(book);
//        book.setAuthor(this);
//    }
//
//    public void removeBook(Book book) {
//        books.remove(book);
//        book.setAuthor(null);
//    }


}
