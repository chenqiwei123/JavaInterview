package com.example.javainterview.Version02;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by riven_chris on 15/7/2.
 * 读-读：能共存
 *
 * 读-写：不能共存
 *
 * 写-写：不能共存
 */
class MyCache{
    private volatile Map<String, Object> cache = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();//独占写，共享读

    public static void main(String[] args) {
        MyCache myCache=new MyCache();
        for (int j=0;j<3;j++){
            new Thread(()->{
                myCache.put("cqw", "sm");
            }).start();
        }
        for (int i=0;i<3;i++){
            new Thread(()->{
                myCache.get("cqw");
            }).start();
        }
    }

    public void put(String key, Object value){
        readWriteLock.writeLock().lock();//独占写
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入数据，独占锁。。");
            cache.put(key, value);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();//释放写锁
            System.out.println(Thread.currentThread().getName()+"\t 正在释放写入的锁。。");
        }
    }

    public void get(final String key){
        System.out.println(Thread.currentThread().getName()+"\t 正在读取数据。。");
        System.out.println("读取出来的数据：Value="+cache.get(key));
    }
}
