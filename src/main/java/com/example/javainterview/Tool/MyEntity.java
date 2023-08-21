package com.example.javainterview.Tool;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

@Data
public class MyEntity {
    private String name;
    private int age;

    public MyEntity() {
    }
    @JsonCreator
    public MyEntity(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
