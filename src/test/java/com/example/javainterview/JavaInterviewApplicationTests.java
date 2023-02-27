package com.example.javainterview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Scanner;

@SpringBootTest
class JavaInterviewApplicationTests {

    @Test
    void contextLoads() {
    }

    // 一维数组存储原理理解
    public static void main(String[] args) {
        // java 基础知识
        // javaBasesInfo();
        // 杨辉三角
        // yanghui();
        // 回型数
        // JavaInterviewApplicationTests();
        // 数组反转
        // reversal();
        // 二分法
        // dichotomy();
        // 冒泡排序
        // BubbleSort();
        // 快速排序
        quicklyOH();
        // Arrays常见方法
        //arraysCommonMethods();


    }

    private static void quicklyOH() {
        int[] arr=new int[]{31,22,4,15,62,8,12,901,13,14,23,999,45,66,77,88,99,104};
        quickSort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
    }

    private static void quickSort(int arr[],int left,int right) {
        if (left<right){
            int a=partition(arr,left,right);
            quickSort(arr,left,a-1);
            quickSort(arr,a+1,right);
        }
    }
    private static int partition(int[] arr,int left,int right) {
        System.out.println("变换前:"+Arrays.toString(arr)+"left="+left+"right="+right);
        if (left>=right){
            return left;
        }
        int pivot = arr[left]; //比较的值
        int directIndex = left; //初始化的index
        boolean isLeft=false;
        for (;left<right;){
            if (isLeft) {
                if (arr[left] > pivot) {
                    swap(arr, directIndex, left);
                    isLeft = false;//right寻找
                    right--;
                    directIndex=left;
                }else {
                    left++;
                }

            }
            if (!isLeft) {
                if (arr[right] < pivot) {
                    swap(arr, directIndex, right);
                    isLeft = true;
                    directIndex = right;
                    left++;
                }else {
                    right--;
                }

            }
        }
        System.out.println("变换后:"+Arrays.toString(arr)+"left="+left+"right="+right);
        return directIndex;
    }
    private static void swap(int[] arr,int i,int j){
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
    }

    private static void arraysCommonMethods() {
        int[] arr=new int[]{1,22,4,15,62,8,901,13,14,23,45,66,77,88,99,104};
        int[] arr1=new int[]{1,22,4,15,62,77,88,99,104};
        // 判断两个数组是否相等
        final boolean equals = Arrays.equals(arr, arr1);
        System.out.println(equals);
        // 打印数组
        System.out.println(Arrays.toString(arr));
        // 数组排序
        Arrays.sort(arr1);
        System.out.println(Arrays.toString(arr1));
        // 数组填充
        Arrays.fill(arr1,135);
        System.out.println(Arrays.toString(arr1));
        // 二分查找
        int index=Arrays.binarySearch(arr,25);
        System.out.println(index);
    }

    private static void BubbleSort() {
        int[] arr = new int[]{1,22,4,15,62,8,901,13,14,23,45,66,77,88,99,104};
        for (int j = 0; j < arr.length-1; j++) {
            for (int i = 0; i < arr.length-1-j; i++) {
                if (arr[i]>arr[i+1]) {
                    arr[i]=arr[i]+arr[i+1];
                    arr[i+1]=arr[i]-arr[i+1];
                    arr[i]=arr[i]-arr[i+1];
                }
            }
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }

    private static void dichotomy() {
        int[] arr = new int[]{1,2,4,5,6,8,9,13,14,23,45,66,77,88,99,104};
        int head=0,end=arr.length-1;
        int target = 45;
        while (true){
            int middle = (head+end)/2;
            if (head>end){
                System.out.println("找不到索引!!");
                break;
            }
            if (target==arr[middle]){
                System.out.println(arr+"的目标值:"+target+"的索引下标值为"+middle);
                break;
            }
            if (target>arr[middle]){
                head=middle+1;
            }
            if (target<arr[middle]){
                end=middle-1;
            }
        }
    }

    private static void reversal() {
        int[] arr =new int[]{1,3,5,7,9,11,13,15,17,19,21};
        for (int i = 0; i < arr.length/2; i++) {
            arr[i]=arr[i]+arr[arr.length-1-i];
            arr[arr.length-1-i]=arr[i]-arr[arr.length-1-i];
            arr[i]=arr[i]-arr[arr.length-1-i];
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf(arr[i]+"\t");
        }
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
