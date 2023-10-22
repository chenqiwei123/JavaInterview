package com.example.javainterview;

import lombok.Data;

import java.io.IOException;
import java.util.Properties;

@Data
public class JavaInterviewQuestion {
    private String properties;
    public static final JavaInterviewQuestion INSTANCE_HUNGRY; // 饿汉式
    static {
        INSTANCE_HUNGRY=new JavaInterviewQuestion();
        Properties proper=new Properties();
        try {
            // 饿汉式通常会出现一些需要读取properties文件一些定义的变量，例如sql的jdbc
            proper.load(JavaInterviewQuestion.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        INSTANCE_HUNGRY.setProperties((String) proper.get("cqw"));
    }

    public static JavaInterviewQuestion INSTANCE_LAZY; // 懒汉式
    public JavaInterviewQuestion getInstanceLazyDemo(){
        if (INSTANCE_LAZY == null ) {
            synchronized (INSTANCE_LAZY) { // 防止初始化实例变量时候，多线程创建多个实例不一致的情况。
                if (INSTANCE_LAZY == null){
                    return INSTANCE_LAZY=new JavaInterviewQuestion();
                }
            }
        }
        return INSTANCE_LAZY;
    }
}
