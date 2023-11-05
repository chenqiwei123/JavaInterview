package com.example.javainterview.Version02;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    private ReentrantLock lock = new ReentrantLock();

    public void method1() {
        lock.lock();
        try {
            System.out.println("method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("method2");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 有几把锁就需要解开几把锁
     */
    public void getLock() {
        lock.lock();
        try {
            System.out.println("getLock");
            lock.lock();
            try {
                System.out.println("getLock2");
            } finally {
                lock.unlock();
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new ReentrantLockDemo().method1();
        new ReentrantLockDemo().getLock();
    }
}

