package com.power.yg.shuzu1.day5.day6;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        //1.数组有length方法吗，String呢
        //length是数组的属性，是String的方法
        //数组为什么索引从0开始？表示的是距离首地址的距离(偏移量)
        // 时间复杂度是什么？只能有并列的for循环，不能嵌套循环

        int[] arr = {3, 22, 5, 3, 66, 2, 9};
        System.out.println("数组为"+Arrays.toString(arr));
        BubbleSort(arr,7);

    }
    //array[]为待排序数组，n为数组长度
    public static void BubbleSort(int array[], int n)
    {
        int i, j, k;
        for(i=0; i<n-1; i++){
            for(j=0; j<n-1-i; j++)
            {
                if(array[j]>array[j+1])
                {
                    k=array[j];
                    array[j]=array[j+1];
                    array[j+1]=k;
                }
            }
            System.out.println("此时的i=" + i + ",j=" + j + ",数组为" + Arrays.toString(array));
        }
    }
}
