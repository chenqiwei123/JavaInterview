package com.example.javainterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class JavaInterviewApplication {

    public static void main(String[] args) throws InterruptedException {
//        SpringApplication.run(JavaInterviewApplication.class, args);
        DeadLock dl = new DeadLock();
        Thread ee=new Thread(dl);
        ee.start();
        dl.init11111();
    }
    static class A {
        public synchronized void foo(B b) {
            System.out.println("当前线程名: " + Thread.currentThread().getName()
                    + " 进入了A实例的foo方法"); // ①
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("当前线程名: " + Thread.currentThread().getName()
                    + " 企图调用B实例的last方法"); // ③
            b.last();
        }

        public synchronized void last() {
            System.out.println("进入了A类的last方法内部");
        }
    }

    static class B {
        public synchronized void bar(A a) {
            System.out.println("当前线程名: " + Thread.currentThread().getName()
                    + " 进入了B实例的bar方法"); // ②
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("当前线程名: " + Thread.currentThread().getName()
                    + " 企图调用A实例的last方法"); // ④
            a.last();
        }

        public synchronized void last() {
            System.out.println("进入了B类的last方法内部");
        }
    }

    public static class DeadLock implements Runnable {
        A a = new A();
        B b = new B();

        public void init11111() {
            Thread.currentThread().setName("主线程");
            // 调用a对象的foo方法
            a.foo(b);
            System.out.println("进入了主线程之后");
        }

        public void run() {
            Thread.currentThread().setName("副线程");
            // 调用b对象的bar方法
            b.bar(a);
            System.out.println("进入了副线程之后");
        }
    }
}
