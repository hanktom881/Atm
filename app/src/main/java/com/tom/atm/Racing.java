package com.tom.atm;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Racing {
    public static void main(String[] args) {
        Horse h1 = new Horse();
        Horse h2 = new Horse();
        Horse h3 = new Horse();
        h1.start();
        h2.start();
        h3.start();
        System.out.println("main end");
    }
}
