package com.example.demo;

import static java.lang.Thread.sleep;

public class Demo1 {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            try{
                sleep(500);
            } catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            MyThread1 myThread1 = new MyThread1("demo-1:");
            myThread1.start();
        }
    }
}
class MyThread1 extends Thread{
    //调用父类的构造方法 设置线程名称
    public MyThread1(String name) {
        super(name);
    }

    @Override
    public void run(){
        System.out.println(Thread.currentThread().getName()+"来修炼啦");
    }
}
