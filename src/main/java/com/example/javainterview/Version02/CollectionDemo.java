package com.example.javainterview.Version02;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionDemo {
    static Vector<Integer> vector=new Vector<>();//线程安全
    private static List<Integer> list=new ArrayList<>();//线程不安全
    private static Set<Integer> hashSet=new HashSet<>();//线程不安全
    private static Map<String,Object> map=new HashMap<>();//线程不安全
//    private static Map<String,Object> map=new ConcurrentHashMap<>(); //线程安全
    public static void main(String[] args) {
        //显示集合线程不安全的示例
        // showCollectionThreadUnsafe();
        //显示HashSet线程不安全的示例
        showHashSetThreadUnsafe();
        // 显示HashMap线程不安全的示例
        showHashMapThreadUnsafe();
    }

    /**
     * 举例展示HashList线程不安全的示例
     */
    private static void showCollectionThreadUnsafe() {
        for (int j=0;j<10;j++) {
            new Thread(()->{
                for (int i = 0; i < 200; i++) {
                    CollectionDemo.list.add(i);
                }
            }).start();
        }
        while (Thread.activeCount()>2){}
        System.out.println(list.size());
    }

    /**
     * 举例展示hashSet线程不安全的示例
     */
    private static void showHashSetThreadUnsafe() {
        for (int j=0;j<10;j++) {
            int finalJ = j+1;
            new Thread(()->{
                for (int i = 0; i < 200; i++) {
                    CollectionDemo.hashSet.add(i* finalJ);
                }
            }).start();
        }
        while (Thread.activeCount()>2){}
        System.out.println(hashSet.size());
    }

    /**
     * 举例展示hashMap线程不安全的示例
     */
    private static void showHashMapThreadUnsafe() {
        for (int j=0;j<10;j++) {
            int finalJ = j+1;
            new Thread(()->{
                for (int i = 0; i < 200; i++) {
                    CollectionDemo.map.put(String.valueOf(Thread.currentThread().getName()+"-"+(i+1)*finalJ),i);
                }
            },"thread-"+j).start();
        }
        while (Thread.activeCount()>2){}
        System.out.println(CollectionDemo.map.size());
    }

}
