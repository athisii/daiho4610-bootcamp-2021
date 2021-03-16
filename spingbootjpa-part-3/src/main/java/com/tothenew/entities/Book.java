package com.tothenew.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    /* OneToOne Shared Primary Key (Author <==>Book) */

    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "author"))
    @Column(name="author_id")
    private Integer id;
    private String bookName;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author = author;
        return this;
    }




//        @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//    private String bookName;

    /*ManyToOne Bidirectional Mapping (Book <==> Author )*/

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Author author;
//
//    public Author getAuthor() {
//        return author;
//    }
//
//    public Book setAuthor(Author author) {
//        this.author = author;
//        return this;
//    }


    public Book() {
    }

    public Book(String bookName) {
        this.bookName = bookName;
    }

    public Integer getId() {
        return id;
    }

    public Book setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public Book setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) && bookName.equals(book.bookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookName);
    }
}
