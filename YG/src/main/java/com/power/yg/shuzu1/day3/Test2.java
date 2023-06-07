package com.power.yg.shuzu1.day3;

public class Test2 {
    public static void main(String[] args) {
        //数组的扩容
        int[] arr = {1,2,3,4,5};
        //将10 20 30 加入数组，数组一旦确定就无法变化，只能new出新的数组
        //可达性分析算法 判断变量和内存地址是否有指针，当栈中变量弹栈出去，指针断裂，堆中的变量就会被gc回收
        int[] newArr = new int[arr.length * 2];
        //位运算向左左移一位
        // int[] newArr = new int[arr.length <<1];

    }
}
