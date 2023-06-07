package com.power.yg.shuzu1.day4;

public class Test1 {
    public static void main(String[] args) {
        //线性查找，查找元素5是否存在
        int[] arr = {1, 5435, 545, 5,234, 554, 6, 5};
        for (int i = 0; i < arr.length; i++) {
            //return   （终止循环并且退出循环所在的方法）
            //continue-终止本次循环。（终止当前循环,进行下一次循环）
            //break-完全结束循环  （终止循环执行循环体下面的代码）
            if (5 == arr[i]) {
                System.out.println("找到了元素5");
                System.out.println("查找了"+(i+1)+"次");
                break;
            }
        }
    }
}
