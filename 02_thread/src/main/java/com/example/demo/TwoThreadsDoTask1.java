package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程去打印0-200 但是当前并非是轮流打印的
 */
public class TwoThreadsDoTask1 {
    static  AtomicInteger integer = new AtomicInteger(0);
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (integer.get() <= 200){
                    System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (integer.get()<=200){
                    System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                }
            }
        }).start();
    }
}
