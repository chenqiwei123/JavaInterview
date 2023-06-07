package com.power.yg.shuzu1.day3;

public class Test1 {

    /*java内存的划分
    * 1.堆
    * 2.栈(stack)
    * 3.本地方法栈
    * 4.程序计数器
    * 5.方法区*/
    public static void main(String[] args) {
        //这段代码在内存中的执行见day2.图一
        int[] arr1 = new int[4];
        arr1[0]=10;
        arr1[1] = 20;
        String[] arr2 = new String[2];
        arr2[1] = "周杰伦";
        arr2 = new String[3];
        //!!!新的例子见图2
    }
}
