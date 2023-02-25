package com.example.javainterview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

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
        //yanghui();
        // 回型数
        JavaInterviewApplicationTests();
    }

    private static void JavaInterviewApplicationTests() {
        System.out.println("请输入需要展示的回型数的圈数:");
        Scanner scanner=new Scanner(System.in);
        int number = scanner.nextInt();
        if (number<1){
            System.out.println("抱歉,您输入的数字非法或者小于1");
        }
        scanner.close();
        int[][] arr = new int[number][number];
        arr[0][0]=1;
        int i = 0,j=0,numberinfo=1;
        while (true){
            int[] digital0=loop(numberinfo,i,j,true,true,arr);
            numberinfo=digital0[0];
            i=digital0[1];
            j=digital0[2];
            if (numberinfo>=number*number){
                break;
            }
            int[] digital1=loop(numberinfo,i,j,false,true,arr);
            numberinfo=digital1[0];
            i=digital1[1];
            j=digital1[2];
            if (numberinfo>=number*number){
                break;
            }
            int[] digital2=loop(numberinfo,i,j,true,false,arr);
            numberinfo=digital2[0];
            i=digital2[1];
            j=digital2[2];
            if (numberinfo>=number*number){
                break;
            }
            int[] digital3=loop(numberinfo,i,j,false,false,arr);
            numberinfo=digital3[0];
            i=digital3[1];
            j=digital3[2];
            if (numberinfo>=number*number){
                break;
            }
        }
        for (int k = 0; k < arr.length; k++) {
            for (int l = 0; l < arr[0].length; l++) {
                System.out.print(arr[k][l]+"\t");
            }
            System.out.println();
        }
    }

    private static int[] loop(int number,int i,int j,boolean X,boolean plus,int[][] arr){
        int temp=0;
        if (X&& plus){
            for (int k =j+1; k < arr[i].length; k++) {
                temp=k;
                if (arr[i][k]==0){
                    number++;
                    arr[i][k]=number;
                }else {
                    temp=temp-1;
                    break;
                }
            }
            j=temp;
        }
        if (X&& !plus){
            for (int k =j-1; k >= 0; k--) {
                temp=k;
                if (arr[i][k]==0){
                    number++;
                    arr[i][k]=number;
                }else {
                    temp=temp+1;
                    break;
                }
            }
            j=temp;
        }
        if (!X&& plus){
            for (int k =i+1; k < arr[i].length; k++) {
                temp=k;
                if (arr[k][j]==0){
                    number++;
                    arr[k][j]=number;
                }else {
                    temp=temp-1;
                    break;
                }
            }
            i=temp;
        }
        if (!X&& !plus){
            for (int k =i-1; k >= 0; k--) {
                temp=k;
                if (arr[k][j]==0){
                    number++;
                    arr[k][j]=number;
                }else {
                    temp=temp+1;
                    break;
                }
            }
            i=temp;
        }
        int[] ints=new int[]{number,i,j};
        return ints;
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
