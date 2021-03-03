//Q5. Demonstrate how you will resolve ambiguity while autowiring bean (Hint : @Primary)


package com.tothenew.question5;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

interface Show {
    public void display();
}

@Component
class A {
    Show s;

    @Autowired
    A(Show s) {
        this.s = s;
    }

    public void display() {
        System.out.println("A");
        s.display();
    }
}

@Component
@Primary
class B implements Show {

    public void display() {
        System.out.println("B");
    }
}

@Component
class C implements Show {
    public void display() {
        System.out.println("C");
    }
}

@Component
@SpringBootApplication
public class Q5 {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Q5.class, args);
        A a = applicationContext.getBean(A.class);
        a.display();

    }
}

