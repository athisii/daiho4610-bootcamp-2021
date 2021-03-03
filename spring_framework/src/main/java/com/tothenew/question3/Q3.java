//Q3. Use @Component and @Autowired annotations to in Loosely Coupled code for dependency management.

package com.tothenew.question3;


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
public class Q3 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Q3.class, args);
        A a = applicationContext.getBean(A.class);
        a.display();
    }
}