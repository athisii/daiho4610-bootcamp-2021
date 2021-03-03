//Q2. Write a program to demonstrate Loosely Coupled code.
/*
Loose coupling:     When an object gets the object to be used from the outside,
                then it is a loose coupling situation. As the main object is merely using the object,
                this object can be changed from the outside world easily marked it as loosely coupled objects.
 */

package com.tothenew.question2;


interface Show {
    public void display();
}

class A {
    Show s;

    public A(Show s) {
        //s is loosely coupled to A
        this.s = s;
    }

    public void display() {
        System.out.println("A");
        s.display();
    }
}

class B implements Show {

    public void display() {
        System.out.println("B");
    }
}

class C implements Show {
    public void display() {
        System.out.println("C");
    }
}

public class Q2 {
    public static void main(String[] args) {
        Show b = new B();
        Show c = new C();

        A a = new A(b);
        a.display();

        A a1 = new A(c);
        a1.display();
    }
}

