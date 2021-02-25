package com.tothenew;

import com.google.common.base.Splitter;


public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
//        splitString("foo,bar, , ,   qux");
    }

    private static void splitString(String inputString) {
        var result = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split(inputString);
        System.out.println(result);
    }
}
