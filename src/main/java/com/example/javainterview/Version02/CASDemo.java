 package com.example.javainterview.Version02;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

 public class CASDemo {
    public volatile Integer indexNumber=0;

     /**
      * 利用CAS的原理，自己制作一个自旋锁
      * @param args
      */
    public static void main(String[] args) {
//        CASLeadInto();//引入CAS的原因，为了解决这类问题。
        CASDemo casDemo=new CASDemo();
        Lock l = new ReentrantLock();
        for(int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<10000;j++) {
                    Integer temp;
                    Boolean flag=false;
                    do{
                        temp=casDemo.indexNumber;
                        if(temp.equals(casDemo.indexNumber)){
                            l.lock();
                                if(temp.equals(casDemo.indexNumber)) {
                                    casDemo.indexNumber = casDemo.indexNumber+1;
                                    flag = true;
                                }
                            l.unlock();
                        }

                    }while(!flag);
                }
            }).start();
        }
        while (Thread.activeCount()>2){

        }
        // 如何解决。可以添加synchronized、atomicInteger.getAndIncrement()、atomicInteger.getAndDecrement()实现原子性。
        System.out.println(casDemo.indexNumber);
    }

     /**
      * 引入CAS的原因，为了解决这个方法出现的问题，
      * 预期casDemo.indexNumber=100000，实际结果远远小于这个值。
      */
    private static void CASLeadInto() {
        // CAS为引入之前可能存在的问题，不能保持原子性，预期casDemo.indexNumber=100000，实际结果远远小于这个值。
        CASDemo casDemo=new CASDemo();
        for(int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<10000;j++) {
                    casDemo.indexNumber++;
                }
            }).start();
        }
        // 如何解决。可以添加synchronized、atomicInteger.getAndIncrement()、atomicInteger.getAndDecrement()实现原子性。
        System.out.println(casDemo.indexNumber);
    }

     /**
      * CAS的用法概念机介绍
      */
    public static void CASConcept(){
        // 创建一个原子类
        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * 一个是期望值，一个是更新值，但期望值和原来的值相同时，才能够更改
         * 假设三秒前，我拿的是5，也就是expect为5，然后我需要更新成 2019
         */
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());
    }
}
