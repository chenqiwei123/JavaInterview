package com.example.javainterview.Version02;

import lombok.Data;

@Data
class DeadLockEntity{
    private String lockA="LOCK_A";
    private String lockB="LOCK_B";

    public DeadLockEntity(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
}
public class DeadLockDemo {
    private static String lockA="LOCK_A";
    private static String lockB="LOCK_B";
    public void DeaDLockRun(String lockA,String lockB){
        synchronized (lockA){
            System.out.println("LOCK_A========================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lockB){
                System.out.println("LOCK_B========================");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            new DeadLockDemo().DeaDLockRun(lockA,lockB);
        }).start();
        new Thread(()->{
            new DeadLockDemo().DeaDLockRun(lockB,lockA);
        }).start();
    }
}
