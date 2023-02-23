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
        // java 基础知识
        //javaBasesInfo();
        // 杨辉三角
        yanghui();
    }

    private static void yanghui() {
        // 杨辉三角
        int[][] yh=new int[10][];
        for (int i = 0; i < yh.length; i++) {
            yh[i]=new int[i+1];
            for (int j = 0; j < yh[i].length; j++) {
                if (j==i || j==0){
                    yh[i][j]=1;
                }else {
                    yh[i][j]=yh[i-1][j]+yh[i-1][j-1];
                }
                System.out.print(yh[i][j]+"\t");
            }
            System.out.println();
        }
    }

    private static void javaBasesInfo() {
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
