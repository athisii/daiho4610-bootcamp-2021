//Q6. Perform Constructor Injection in a Spring Bean.


package com.tothenew.question6;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
class A {
    B b;

    @Autowired
    A(B b) {
        this.b = b;
    }

    public void display() {
        System.out.println("A");
        b.display();
    }
}

@Component
class B {
    public void display() {
        System.out.println("B");
    }
}


@Component
@SpringBootApplication
public class Q6 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Q6.class, args);
        A a = applicationContext.getBean(A.class);
        a.display();

    }
}

