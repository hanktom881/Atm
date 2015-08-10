package com.tom.atm;

/**
 * Created by Administrator on 2015/8/10.
 */
public class HorseRunnable implements Runnable{
    String name;
    public HorseRunnable(String name){
        this.name = name;
    }
    @Override
    public void run() {
        for (int i=0; i<100; i++) {
            System.out.println(name+":" + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
