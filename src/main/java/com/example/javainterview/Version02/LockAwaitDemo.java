package com.example.javainterview.Version02;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：多线程之间按顺序调用，实现 A-> B -> C 三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * ..
 * 来10轮
 */
public class LockAwaitDemo {
    private String thisThreadName="A";
    private Lock lockDemo = new ReentrantLock();
    public Condition conditionA=lockDemo.newCondition();
    public Condition conditionB=lockDemo.newCondition();
    public Condition conditionC=lockDemo.newCondition();

    public static void main(String[] args) {
        LockAwaitDemo lockAwaitDemo = new LockAwaitDemo();
         for(int i=0;i<10;i++) {
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"A");
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"B");
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"C");
        }
    }

    public void getThreadRun(LockAwaitDemo lockAwaitDemo,String threadName){
        new Thread(()->{
            lockDemo.lock();
            while (!lockAwaitDemo.thisThreadName.equals(threadName)){
                try {
                    if ("A".equals(threadName)){
                        conditionA.await();
                    }
                    if ("B".equals(threadName)){
                        conditionB.await();
                    }
                    if ("C".equals(threadName)){
                        conditionC.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("A".equals(threadName)){
                System.out.println("---------------开始----------------");
                lockAwaitDemo.thisThreadName="B";
                lockAwaitDemo.print5(5);
                conditionB.signal();
            }
            if ("B".equals(threadName)){
                lockAwaitDemo.thisThreadName="C";
                lockAwaitDemo.print5(10);
                conditionC.signal();
            }
            if ("C".equals(threadName)){
                lockAwaitDemo.thisThreadName="A";
                lockAwaitDemo.print5(15);
                conditionA.signal();
                System.out.println("---------------结束----------------");
            }
            lockDemo.unlock();
        },threadName).start();
    }

    public void print5(int number){
        for (int j=1;j<=number;j++) {
            System.out.println(Thread.currentThread().getName()+"\t "+j);
        }
    }

}
