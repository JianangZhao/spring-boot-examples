package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程去打印0-200 但是当前并非是轮流打印的
 * 如果需要轮流打印 需要一个状态 或者 对线程进行堵塞
 * 通过对线程进行堵塞 实现 线程的轮流打印
 */
public class TwoThreadsDoTask3 {
    static  AtomicInteger integer = new AtomicInteger(0);
    static final Object object = new Object();
    public static void main(String[] args) {

        new Thread(() -> {
            while (integer.get() <= 200){
                synchronized (object){
                    object.notify();
                    System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        new Thread(() -> {
            while (integer.get()<=200){
                synchronized (object){
                    object.notify();
                    System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                    try {
                        object.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }
}
