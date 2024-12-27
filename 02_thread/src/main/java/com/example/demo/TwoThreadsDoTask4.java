package com.example.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 三个线程轮流打印
 */
public class TwoThreadsDoTask4 extends Thread{
    private static int count = 0; //程序计数器
    private static final Object object = new Object(); //锁对象
    private final int threadId; // 线程ID

    public TwoThreadsDoTask4(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            synchronized (object){
                while (count % 3 != threadId){ //确保当前线程在打印
                    try {
                        object.wait(); // 当前线程等待
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("Thread " + threadId + ": " + i);
                count++;
                object.notifyAll(); // 唤醒其他线程
            }
        }
    }

    public static void main(String[] args) {
        TwoThreadsDoTask4 thread0 = new TwoThreadsDoTask4(0);
        TwoThreadsDoTask4 thread1 = new TwoThreadsDoTask4(1);
        TwoThreadsDoTask4 thread2 = new TwoThreadsDoTask4(2);

        thread0.start();
        thread1.start();
        thread2.start();
    }
}
