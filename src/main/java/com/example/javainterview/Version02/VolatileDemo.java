package com.example.javainterview.Version02;


import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    public static void main(String[] args) {
        // 可见性的代码验证，正常情况。多个线程拿到主内存的同一变量数据，某个线程修改了这个变量数值，那么其他的变量是没有感知的。
        ObjectDemo objectDemo =new ObjectDemo();
        Thread t1=new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            objectDemo.addNumber();
        });
        t1.start();//t1线程启动，main线程将ints[0]变量拷贝主内存。t1线程修改了主内存的ints[0]，
        // t1线程退出，判断这个时候的main线程是否感知到这个变量已经修改了。
        // 1.线程之间的不可见性。
//        while (objectDemo.a==1&&t1.isAlive()){// t1.isAlive()判断这个线程是否存活，可以去掉查看，就是个死循环了。
//            // 这个时候执行这个循环的时候的objectDemo.a=1保存在缓存中，每次拿的都是缓存中的数据
//            System.out.println(Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
//        }
        // 在这一步是进行重新拿主内存的共享变量拷贝一份在工作内存
        System.out.println(Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
        // 2. Volatile的可见性
        while (objectDemo.a==1){// t1.isAlive()判断这个线程是否存活，可以去掉查看。这个时候执行这个循环的时候的objectDemo.a=1保存在缓存中，每次拿的都是缓存中的数据

        }
        System.out.println("Volatile的可见性:"+Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
    }
}

class ObjectDemo {
//    public int a=1; //不可见性
    public volatile int a=1; // Volatile的可见性
    public void addNumber(){
        this.a=60;
    }

    @Override
    public String toString() {
        return "ObjectDemo{" +
                "a=" + a +
                '}';
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}

