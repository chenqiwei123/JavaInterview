package com.power.yg.duixiang2.day3;

public class Test1 {
    public static void main(String[] args) {
        //可变形参使用 数据类型 ... 参数名
        //参数String ... str 等同于 String [] arr 会造成重载冲突
        //可变形参必须是方法参数的最后一个
        String test = test(5, "hhh", "kkkk", "fsafa");
        System.out.println(test);
    }

    public static String test(int a,  String ... strings) {
        String result = "";
        for (int i = 0; i < strings.length; i++) {
            result += strings[i];
        }
        result = "第一个参数为:" + a +",后面的参数为:"+ result;
        return result;
    }
}
