package com.tom.atm;

/**
 * Created by Administrator on 2015/8/10.
 */
public class Horse extends Thread{
    @Override
    public void run() {
        for (int i=0; i<100; i++) {
            System.out.println(getName()+":" + i);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
