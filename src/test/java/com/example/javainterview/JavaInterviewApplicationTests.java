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
    }
}
