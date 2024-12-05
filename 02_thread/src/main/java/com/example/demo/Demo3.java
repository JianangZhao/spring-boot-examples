package com.example.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class Demo3 {
    public static void main(String[] args) {
        MyThread3 myThread3 = new MyThread3();
        for (int i = 0; i < 10; i++) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            FutureTask futureTask = new FutureTask(myThread3);
            new Thread(futureTask).start();
        }
    }
}
class MyThread3 implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"来修炼啦");
        return null;
    }
}