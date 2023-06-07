package com.power.yg.shuzu1.day4;

public class Test2 {
    public static void main(String[] args) {
        int[] arr = {1, 434, 4554, 5, 53, 43, 143, 657, 23, 76, 234, 657, 24};
        //数组排序，并使用二分查找法
        //冒泡排序法
        int temp;
        for (int i = 0; i < arr.length; i++) {
            for (int k = i+1; k < arr.length; k++) {
                if (arr[i] > arr[k]) {
                    temp = arr[i];
                    arr[i] = arr[k];
                    arr[k] = temp;
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
