package com.power.yg.shuzu1.day2;

public class Test1 {
    public static void main(String[] args) {
         //定义一个长度为10的数组求出他们的最大值，最小值，平均数，总和
        int[] arr = new int[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=(int)(Math.random()*100);
        }
        //最大值max
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        System.out.println("最大值为"+max);
        //最小值min
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        System.out.println("最小值为"+min);

        //总和
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum+=arr[i];
        }
        System.out.println("总和为"+sum);
        //平均值
        float avg=(float)(sum / arr.length);
        System.out.println("平均值为"+avg);
    }
}
