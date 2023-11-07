package com.example.javainterview.Version02;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        Semaphore semaphore =new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 进来了！！！");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 我走了！！");
                semaphore.release();
            }).start();
        }
        while (Thread.activeCount()>2) {

        }
        System.out.println("-----------------------------");
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"\t 进来了！！！");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 我走了！！");
                countDownLatch.countDown();
            }).start();
        }
        // 计数器为0, 等待所有线程执行完毕
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t "+"所有线程都执行完毕了！！");
    }
}
