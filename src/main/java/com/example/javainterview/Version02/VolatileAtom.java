package com.example.javainterview.Version02;

import java.util.concurrent.atomic.AtomicInteger;

public class VolatileAtom {
    int b = 0;
    public synchronized void Add(){
        this.b++;
    }
    public static void main(String[] args) {
        VolatileAtom atom = new VolatileAtom();
        AtomicInteger atomicInteger =new AtomicInteger(); // 创建原子Integer的包装类，默认为0;
        Thread t1=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.Add();
                atomicInteger.getAndIncrement();
            }
        });
        t1.start();
        Thread t2=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.Add();
                atomicInteger.getAndIncrement();
            }
        });
        t2.start();
        try {
            t1.join(); //Waits for t1 thread to die
            t2.join();//Waits for t2 thread to die
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 我们按预期应该打印出2000000，但是为什么只打印出了  main 	 atom.b: 	 886597。每次执行效果还不一样呢。这就是volatile的不保证可见性，还不太明白看我下面娓娓道来。
        System.out.println(Thread.currentThread().getName()+" \t atom.b: \t "+atom.b);// main 	 atom.b: 	 886597
        System.out.println(Thread.currentThread().getName()+" \t atomicInteger: \t "+atomicInteger);// main 	 atom.b: 	 886597

    }
}
