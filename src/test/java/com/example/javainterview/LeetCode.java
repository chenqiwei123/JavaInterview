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

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     *
     * 你可以按任意顺序返回答案。
     *
     * 示例 1：
     *
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * 示例 2：
     *
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     * 示例 3：
     *
     * 输入：nums = [3,3], target = 6
     * 输出：[0,1]
     * 提示：
     *
     * 2 <= nums.length <= 104
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * 只会存在一个有效答案
     */
    @Test
    public void twoSumList(){
        int[] ints=new int[]{-3,4,3,90};
        int[] show=twoSum(ints,0);
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        int[] addNums=new int[2];
        for (int i=0;i<nums.length;i++) {
                if (null!=map.get(target-nums[i])){
                    addNums[0]=map.get(target-nums[i]);
                    addNums[1]=i;
                    return addNums;
                }else {
                    map.put(nums[i],i);
                }
        }
        return addNums;
    }

}
