//Q4. Get a Spring Bean from application context and display its properties.


package com.tothenew.question4;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

interface Show {
    public void display();
}

@Component
class A {
    @Autowired
    Show s;

    public void display() {
        System.out.println("A");
        s.display();
    }
}


@Component
class B implements Show {

    public void display() {
        System.out.println("B");
    }
}


@Component
@SpringBootApplication
public class Q4 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Q4.class, args);
        A a = applicationContext.getBean(A.class);
        B b = applicationContext.getBean(B.class);
        a.display();
        b.display();
    }
}