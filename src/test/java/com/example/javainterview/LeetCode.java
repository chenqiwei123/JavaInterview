package com.example.javainterview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class LeetCode {

    /**
     * 给你一个整数数组 nums ，返回出现最频繁的偶数元素。
     *
     * 如果存在多个满足条件的元素，只需要返回 最小 的一个。如果不存在这样的元素，返回 -1 。
     *
     * 示例 1：
     *
     * 输入：nums = [0,1,2,2,4,4,1]
     * 输出：2
     * 解释：
     * 数组中的偶数元素为 0、2 和 4 ，在这些元素中，2 和 4 出现次数最多。
     * 返回最小的那个，即返回 2 。
     * 示例 2：
     *
     * 输入：nums = [4,4,4,9,2,4]
     * 输出：4
     * 解释：4 是出现最频繁的偶数元素。
     * 示例 3：
     *
     * 输入：nums = [29,47,21,41,13,37,25,7]
     * 输出：-1
     * 解释：不存在偶数元素。
     * 提示：
     *
     * 1 <= nums.length <= 2000
     * 0 <= nums[i] <= 105
     */
    @Test
    public void EvenMostAndsmallest(){
        int[] test=new int[]{29,47,21,41,13,37,25,7,3,56,34,6,6,6,9,3,20,34,5,67,432,67,56,2,2,4,4,6,6};
        System.out.println("最频繁最小的的偶数元素"+mostFrequentEven(test));
    }
    public int mostFrequentEven(int[] nums) {
        Map<Integer,Integer> map =new HashMap<>();
        boolean flag=false;
        Integer num=0;
        Integer index=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]%2==0){
                flag=true;
                map.put(nums[i],map.getOrDefault(nums[i],0)+1);
            }
        }
        if (!flag){
            return -1;
        }
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            Integer key=integerIntegerEntry.getKey();
            if (map.get(key)>num){
                num=map.get(key);
                index=key;
            }
            if (map.get(key)==num&&key<index){
                num=map.get(key);
                index=key;
            }
        }
        return index;
    }
}
