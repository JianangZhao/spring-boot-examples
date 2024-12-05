package com.example.demo;

import static java.lang.Thread.sleep;

public class Demo2 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try{
                sleep(500);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            MyThread2 myThread2 = new MyThread2();
            Thread thread = new Thread(myThread2);

            thread.start();
        }
    }
}
class MyThread2 extends Thread{
    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+"来修炼啦");
    }
}
