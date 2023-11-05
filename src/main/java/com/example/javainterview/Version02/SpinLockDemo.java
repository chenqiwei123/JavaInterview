package com.example.javainterview.Version02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    public void myLock() {
        System.out.println(Thread.currentThread().getName()+"\t myLock");
        Thread current = Thread.currentThread();
        while (!owner.compareAndSet(null, current)) {
            // 自旋
        }
    }

    public void unMyLock(){
        System.out.println(Thread.currentThread().getName()+"\t unMyLock");
        owner.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo =new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLockDemo.unMyLock();
        },"AAAAA").start();
        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.unMyLock();
        },"BBBBB").start();
    }
}
