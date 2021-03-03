//Q1. Write a program to demonstrate Tightly Coupled code.

/*

Tight Coupling:     When an object creates the object to be used, then it is a tight coupling situation.
                As the main object creates the object itself, this object can not be changed from outside
                world easily marked it as tightly coupled objects.
 */


package com.tothenew.question1;


class A {
    B b;

    public A() {
        //b is tightly coupled to A
        b = new B();
    }

    public void display() {
        System.out.println("A");
        b.display();
    }
}

class B {
    public void display() {
        System.out.println("B");
    }
}


public class Q1 {
    public static void main(String[] args) {
        A a = new A();
        a.display();
    }
}

