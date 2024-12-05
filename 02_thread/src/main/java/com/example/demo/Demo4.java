package com.example.demo;

import java.util.concurrent.*;

public class Demo4 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,20,5,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(40),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            MyThread4 myThread4 = new MyThread4();
            threadPoolExecutor.submit(myThread4);
        }
    }
}
class MyThread4 implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"来修炼啦");
        return null;
    }
}