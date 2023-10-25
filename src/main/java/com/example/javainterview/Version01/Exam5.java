package com.example.javainterview.Version01;

public class Exam5 {
    static int s;// 5
    int i; // A-2  B-1
    int j;//A-1 B-1
    {
        int i = 1;
        i++; // 就近原则
        j++;
        s++;
    }
    public void test(int j) {
        j++; // 就近原则 21
        i++;
        s++;
    }
    public static void main(String[] args){
        Exam5 obj1 = new Exam5();
        Exam5 obj2 = new Exam5();
        obj1.test(10);
        obj1.test(20);
        obj2.test(30);
        // 2 1 5
        System.out.println(obj1.i + "," + obj1.j + "," + obj1.s);
        // 1 1 5
        System.out.println(obj2.i + "," + obj2.j + "," + obj2.s);
    }
}
