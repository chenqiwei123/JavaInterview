package com.power.yg.duixiang2.day3;

import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) {
        //对于基本数据类型只进行值的传递(在栈中直接变量赋值),所以改变只会改变本身的值
        int m = 10;
        int n = m;
        n++;
        System.out.println("m=" + m + ",n=" + n);
        //对于引用数据类型进行地址的传递，在堆中公用一个地址，所以改变了都会改变
        int[] arr = {1, 2, 3};
        int[] arr1 = arr;
        arr1[1] = 4;
        System.out.println("arr=" + Arrays.toString(arr) + ",arr1=" + Arrays.toString(arr1));
    }
}
