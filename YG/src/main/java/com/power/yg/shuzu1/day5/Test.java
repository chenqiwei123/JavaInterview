package com.power.yg.shuzu1.day5;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        //数组的工具类
        //1.数组比较是否相同
        int[] arr = {1,2,1,2};
        int[] arr2 = {1,2,1,2};
        System.out.println(Arrays.equals(arr, arr2));
        //2.数组内容的查看
        System.out.println(Arrays.toString(arr));
        //3.将值填充到数组里,将所有值替换为当前值
        Arrays.fill(arr,10);
        System.out.println(Arrays.toString(arr));
        //4.使用数组的排序
        int[] arr3 = {88, 54, 111, 32};
        Arrays.sort(arr3);
        System.out.println(Arrays.toString(arr3));
        //5.二分查找法，指定值查找它的下标
        int i = Arrays.binarySearch(arr3, 111);
        System.out.println("111的下标为" + i);
        /*
        * 数组的异常:
        *   1.下标越界
        *   2.空指针异常
        * */
        //下标越界
        int[] arr5 = new int[10];
        System.out.println(arr5[11]);
        //空指针异常:其实就是变量和内存地址之间的指针关系消失，找不到指向关系
        arr5 = null;
        System.out.println(arr5[1]);
    }
}
