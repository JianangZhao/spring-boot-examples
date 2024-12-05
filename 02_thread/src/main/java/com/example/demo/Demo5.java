package com.example.demo;

import static java.lang.Thread.sleep;

public class Demo5 implements Runnable{
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try{
                sleep(500);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            MyThread1 myThread1 = new MyThread1("demo1");
            myThread1.start();
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"来修炼啦");
    }
}
