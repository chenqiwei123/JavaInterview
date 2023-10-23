package com.example.javainterview.Version01;

import lombok.Data;

@Data
public class Son extends Parent{
    private String sex=getNameInfo();
    private static String num=getAgeInfo();
    static {
        System.out.println(6);
    }
    {
        System.out.println(7);
    }
    public Son(){
        System.out.println(8);
    }
    public String getNameInfo(){
        System.out.println(9);
        return "9";
    }
    public static String getAgeInfo(){
        System.out.println(10);
        return "10";
    }

}
