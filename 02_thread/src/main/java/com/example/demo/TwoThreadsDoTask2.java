package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程去打印0-200 但是当前并非是轮流打印的
 * 如果需要轮流打印 需要一个状态 或者 对线程进行堵塞
 * 通过添加状态 实现 线程的轮流打印
 */
public class TwoThreadsDoTask2 {
    static  AtomicInteger integer = new AtomicInteger(0);
    static boolean  flag = false;
    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (integer.get() <= 200){
                    if (flag){
                        System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                        flag = false;
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (integer.get()<=200){
                    if (!flag){
                        System.out.println(Thread.currentThread().getName() + " do task :" + integer.getAndIncrement());
                        flag = true;
                    }
                }
            }
        }).start();
    }
}
