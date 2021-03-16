package com.tothenew.services;

import com.tothenew.entities.Address;
import com.tothenew.entities.Author;
import com.tothenew.entities.Book;
import com.tothenew.entities.Subject;
import com.tothenew.repos.AuthorRepository;
import com.tothenew.repos.SubjectRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class Bootstrap implements ApplicationRunner {
    @Autowired
    private AuthorRepository authorRepository;


    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {

        Subject subject1 = new Subject("Computer Science");
        Subject subject2 = new Subject("Mathematics");
        Subject subject3 = new Subject("Physics");
        List<Subject> subjects = List.of(subject1, subject2, subject3);

        Author author1 = addAuthor("Tom", 1, "Imphal", "Manipur");
        author1.setSubjects(subjects);

        Author author2 = addAuthor("Rose", 2, "Mumbai", "Maharashtra");
        author2.setSubjects(subjects);


        /* OneToOne Bidirectional bidirectional Shared Primary Key Mapping (Author <==> Book) */
        Book book1 = new Book("Clean Code");
        author1.setBook(book1);
        book1.setAuthor(author1);

        Book book2 = new Book("Design Patterns");
        author2.setBook(book2);
        book2.setAuthor(author2);


        authorRepository.save(author1);
        authorRepository.save(author2);


        /* OneToMany Unidirectional Mapping (Author ==> Book) */
//        Author author3 = addAuthor("Jesse", 1, "Imphal", "Manipur");
//        List<Book> books = new ArrayList<>();
//        books.add(new Book("Spring Data JPA"));
//        books.add(new Book("Java Fundamentals"));
//        author3.setBooks(books);
//
//        Author author4 = addAuthor("Rose", 2, "Mumbai", "Maharashtra");
//        List<Book> books1 = new ArrayList<>();
//        books1.add(new Book("Clean Code"));
//        books1.add(new Book("Design Patterns"));
//        author4.setBooks(books1);
//
//        authorRepository.save(author3);
//        authorRepository.save(author4);



        /* OneToMany Bidirectional Mapping (Author <==> Book) */
//        Author author5 = addAuthor("Jesse", 1, "Imphal", "Manipur");
//        author5.addBook(new Book("Coders At Work"));
//        author5.addBook(new Book("Complete Code"));
//
//        Author author6 = addAuthor("Rose", 2, "Mumbai", "Maharashtra");
//        author6.addBook(new Book("Python Tricks"));
//        author6.addBook(new Book("Head First Javascript"));
//
//        authorRepository.save(author5);
//        authorRepository.save(author6);





        /* ManyToMany Mapping (Author <==> Book) */

//        Author author7 = addAuthor("Tom", 1, "Imphal", "Manipur");
//        author7.setSubjects(subjects);
//
//        Author author8 = addAuthor("Rose", 2, "Mumbai", "Maharashtra");
//        author8.setSubjects(subjects);
//
//        Book book1 = new Book("Spring Data JPA");
//        Book book2 = new Book("Java Fundamentals");
//
//        List<Book> books = new ArrayList<>();
//        books.add(book1);
//        books.add(book2);
//
//        author7.setBooks(books);
//        author8.setBooks(books);
//        authorRepository.save(author7);
//        authorRepository.save(author8);


    }

    private Author addAuthor(String authorName, Integer streetNumber, String location, String state) {
        Author author = new Author();
        author.setName(authorName);

        Address address = new Address();
        address.setStreetNumber(streetNumber);
        address.setLocation(location);
        address.setState(state);
        author.setAddress(address);
        return author;
    }
}
