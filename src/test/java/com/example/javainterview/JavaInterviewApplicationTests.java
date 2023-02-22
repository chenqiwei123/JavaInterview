package com.example.javainterview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JavaInterviewApplicationTests {

    @Test
    void contextLoads() {
    }

    // 一维数组存储原理理解
    public static void main(String[] args) {
        int[] arr = new int[3];
        arr[0]=1;
        arr[1]=2;
        arr[2]=3;
        int[] arr1=arr;
        arr1[2]=100;
        System.out.printf(String.valueOf(arr[2]));
        System.out.println(arr); //[I@15aeb7ab  [代表一维数组  T 代表Int类型  @表示在哪 15aeb7ab表示地址
        System.out.println(arr1); //[I@15aeb7ab  [代表一维数组  T 代表Int类型  @表示在哪 15aeb7ab表示地址
        int[] ints=new int[3];
        System.out.println(ints[0]);
        int[][] str = new int[3][];
        System.out.println(str[1]); //null
        //System.out.println(str[0][0]); //报错了
    }
}
