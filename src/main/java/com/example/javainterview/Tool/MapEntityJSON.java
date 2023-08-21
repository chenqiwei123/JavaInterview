package com.example.javainterview.Tool;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体类和Map及JSON相互转换
 */
public class MapEntityJSON {
    public static void main(String[] args) {
        try {
            new MapEntityJSON().Map2Entity2JSON();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Map和实体类及JSON相关转换
     * @throws JsonProcessingException
     */
    public void Map2Entity2JSON() throws JsonProcessingException {
        // Map转换为实体类
        Map<String, Object> map = new HashMap<>();
        map.put("name", "John");
        map.put("age", 25);

        ObjectMapper mapper = new ObjectMapper();
        MyEntity entity = JSON.parseObject(JSON.toJSONString(map),MyEntity.class);;
        System.out.println("Map to Entity: " + entity.getName() + ", " + entity.getAge());

        // 实体类转换为Map
        MyEntity entity2 = new MyEntity("Jane", 30);
        Map<String, Object> map2= JSON.parseObject(JSON.toJSONString(entity2), new TypeReference<Map<String, Object>>() {});
        System.out.println("Entity to Map: " + map2);

        // 实体类转换为JSON
        JSONObject json = JSON.parseObject(JSON.toJSONString(entity2));
        System.out.println("Entity to JSON: " + json);

        // JSON转换为实体类
        MyEntity entity3 = JSON.parseObject(JSON.toJSONString(entity2),MyEntity.class);
        System.out.println("JSON to Entity: " + entity3.getName() + ", " + entity3.getAge());
    }

}
