package com.tom.atm;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Racing2 {
    public static void main(String[] args) {
        HorseRunnable h1 = new HorseRunnable("Horse1");
        HorseRunnable h2 = new HorseRunnable("Horse2");
        Thread thr1 = new Thread(h1);
        Thread thr2 = new Thread(h2);
        thr1.start();
        thr2.start();

    }
}
