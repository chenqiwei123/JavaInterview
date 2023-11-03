package com.example.javainterview.Version02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABASolve  {
    public static AtomicInteger atomicInteger=new AtomicInteger(100);
    public static AtomicStampedReference<Integer> atomicStampedReference=new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        // ABA 问题的产生
        new ABASolve().ABAProblem();
        // ABA 问题解决- 增加版本号
        new ABASolve().ABAProblemSolve();

    }

    /**
     * ABA问题的解决，添加版本号。AtomicStampedReference进行解决
     * A线程已经将变量A的值从100改为101，版本号+1，又改回了100版本号再次+1。B线程将变量A的值试图将100，发现版本号不一样，修改不成功。
     */
    public void ABAProblemSolve(){
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicStampedReference.compareAndSet(100,101,1,2);
            ABASolve.atomicStampedReference.compareAndSet(101,100,2,3);

        },"A").start();
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicStampedReference.compareAndSet(101,102,1,2);
        },"B").start();
        while (Thread.activeCount()>2){

        }
        System.out.println(ABASolve.atomicStampedReference.getReference());
    }

    /**
     * ABA问题的产生
     * A线程已经将变量A的值从100改为101，又改回了100。但是B线程也能将变量A的值从100(A现成修改后的100了)改为102，
     */
    public void ABAProblem(){
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicInteger.compareAndSet(100,101);
            ABASolve.atomicInteger.compareAndSet(101,100);

        },"A").start();
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicInteger.compareAndSet(100,102);
        },"B").start();
        while (Thread.activeCount()>2){

        }
        System.out.println(ABASolve.atomicInteger);
    }
}
