package com.power.yg.shuzu1.day1;

public class Test1 {
    public static void main(String[] args) {
        //1.数组的特点：1)在内存中连续不间断的空间;2）可以包含基本数据类型也可以包含引用数据类型;3)长度不可以进行变化
        //2.数组的写法
        //第一种写法：静态初始化（同时确定长度和内部元素）
        Double[] arr = new Double[]{1d,2d,3d};
        //第二种写法，先不写元素但是必须写长度进行初始化：动态初始化(只确定长度不确定内部元素)
        Double[] arr2 = new Double[10];
        //第三种方式
        String[] arr3 = {"a", "b", "c"};
        /*
        * 3.数组元素的调用：通过下标(索引)来获取
        *   1）下标从0开始，到数组长度-1结束
        *   2）获取下标时超过则下标越界
        *    */
        //通过下标赋值
        int[] arr4 = new int[4];
        arr4[0] = 1;
        arr4[1] = 2;
        arr4[2] = 3;
        arr4[3] = 4;
        /*
         * 4.数组的长度.length;
         * 注意：不是方法没有()，是属性
         */
        int length=arr4.length;
        /*
         *5.如何遍历数组
         */
        for (int i = 0; i < arr4.length; i++) {
            //System.out.println(arr4[i]);
        }
         /*
         * 6.数组元素的默认初始化值,包装类型均为null
         */
        //short，byte，int，long类型,初始化值0
        int[] prince = new int[5];
        System.out.println(prince[0]);
        //float为0.0
        float [] prince1 = new float[5];
        System.out.println(prince1[0]);
        //double类型,0.0
        double[] prince2 = new double[4];
        System.out.println(prince2[0]);
        //char类型,0或者\u0000
        char[] prince3 = new char[3];
        System.out.println(prince3[0]==0);
        //boolean类型，false
        boolean[] prince4 = new boolean[4];
        System.out.println(prince4[0]);
        //string类型，null
        String[] prince5 = new String[4];
        System.out.println(prince5[0]);
        /*
         * 6.数组内存解构
         */

    }

}
