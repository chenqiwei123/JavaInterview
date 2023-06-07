package com.power.yg.duixiang2.day3;

public class Test3 {
    private int page;
    public static void main(String[] args) {
        int m = 10;
        test(m);
        //main方法压栈。test方法压栈，属于不同的m
        //test方法中的m值改变以后弹栈消失，留下来的还是初始的m
        System.out.println("m=" + m);
        //这是指向堆中test3的变量，当堆中page变量始终一个，所以改变了
        Test3 test3 = new Test3();
        test3.page = 10;
        test3.get(test3);
        System.out.println("test3.page="+test3.page);
        //跟静态方法没有关系！！！
    }

    public static void test(int m ) {
        m++;
    }

    public void get(Test3 test3) {
        test3.page++;
    }

}
