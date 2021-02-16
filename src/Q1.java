/*
    Q1. Create Java classes having suitable attributes for Library management system.
       Use OOPs concepts in your design.Also try to use interfaces and abstract classes.
*/

// Should have created a package and put in different classes,
// but for simplicity I just packed all the classes, interfaces here.


import java.util.Date;
import java.util.HashMap;
import java.util.List;


class Person {
    private String name;
    private Address address;
    private String email;
    private String phone;
}

class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}


abstract class Account {
    private String id;
    private String password;
    private Person person;

    public boolean resetPassword() {
        return true;
    }
}


class Librarian extends Account {
    public boolean addBookItem(BookItem bookItem) {
        return true;
    }

    public boolean blockMember(Member member) {
        return true;
    }

    public boolean unBlockMember(Member member) {
        return true;
    }
}

class Member extends Account {
    private Date dateOfMembership;
    private int totalBooksCheckedout;

}

abstract class Book {
    private String ISBN;
    private String title;
    private String subject;
    private String author;
    private String publisher;
    private String language;
    private int numberOfPages;
}

class BookItem extends Book {
    private String barcode;
    private boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    private double price;
    private Date dateOfPurchase;
    private Date publicationDate;
}


interface Search {
    public List<Book> searchByTitle(String title);
    public List<Book> searchByAuthor(String author);
    public List<Book> searchBySubject(String subject);
    public List<Book> searchByPubDate(Date publishDate);
}

abstract class Catalog implements Search {
    private HashMap<String, List<Book>> bookTitles;
    private HashMap<String, List<Book>> bookAuthors;
    private HashMap<String, List<Book>> bookSubjects;
    private HashMap<String, List<Book>> bookPublicationDates;
}


public class Q1 {
    public static void main(String[] args) {
        System.out.println("Simplified Java Classes for Library Management System");
    }
}

