package com.example.javainterview.Version01;

import lombok.Data;

@Data
public class Parent {
    private String name=getNameInfo();
    private static String age=getAgeInfo();
    static {
        System.out.println(1);
    }
    {
        System.out.println(2);
    }
    public Parent(){
        System.out.println(3);
    }
    public String getNameInfo(){
        System.out.println(4);
        return "4";
    }
    public static String getAgeInfo(){
        System.out.println(5);
        return "5";
    }
}
