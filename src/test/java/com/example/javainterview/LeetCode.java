package com.example.javainterview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class LeetCode {

    /**
     * 给你一个整数数组 nums ，返回出现最频繁的偶数元素。
     * <p>
     * 如果存在多个满足条件的元素，只需要返回 最小 的一个。如果不存在这样的元素，返回 -1 。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [0,1,2,2,4,4,1]
     * 输出：2
     * 解释：
     * 数组中的偶数元素为 0、2 和 4 ，在这些元素中，2 和 4 出现次数最多。
     * 返回最小的那个，即返回 2 。
     * 示例 2：
     * <p>
     * 输入：nums = [4,4,4,9,2,4]
     * 输出：4
     * 解释：4 是出现最频繁的偶数元素。
     * 示例 3：
     * <p>
     * 输入：nums = [29,47,21,41,13,37,25,7]
     * 输出：-1
     * 解释：不存在偶数元素。
     * 提示：
     * <p>
     * 1 <= nums.length <= 2000
     * 0 <= nums[i] <= 105
     */
    @Test
    public void EvenMostAndsmallest() {
        int[] test = new int[]{29, 47, 21, 41, 13, 37, 25, 7, 3, 56, 34, 6, 6, 6, 9, 3, 20, 34, 5, 67, 432, 67, 56, 2, 2, 4, 4, 6, 6};
        System.out.println("最频繁最小的的偶数元素" + mostFrequentEven(test));
    }

    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        boolean flag = false;
        Integer num = 0;
        Integer index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                flag = true;
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
        }
        if (!flag) {
            return -1;
        }
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            Integer key = integerIntegerEntry.getKey();
            if (map.get(key) > num) {
                num = map.get(key);
                index = key;
            }
            if (map.get(key) == num && key < index) {
                num = map.get(key);
                index = key;
            }
        }
        return index;
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target 的那 两个 整数，并返回它们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * <p>
     * 你可以按任意顺序返回答案。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * 示例 2：
     * <p>
     * 输入：nums = [3,2,4], target = 6
     * 输出：[1,2]
     * 示例 3：
     * <p>
     * 输入：nums = [3,3], target = 6
     * 输出：[0,1]
     * 提示：
     * <p>
     * 2 <= nums.length <= 104
     * -109 <= nums[i] <= 109
     * -109 <= target <= 109
     * 只会存在一个有效答案
     */
    @Test
    public void twoSumList() {
        int[] ints = new int[]{-3, 4, 3, 90};
        int[] show = twoSum(ints, 0);
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] addNums = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (null != map.get(target - nums[i])) {
                addNums[0] = map.get(target - nums[i]);
                addNums[1] = i;
                return addNums;
            } else {
                map.put(nums[i], i);
            }
        }
        return addNums;
    }

    @Test
    public void main() {
        ListNode l1 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null)))))));
        ListNode l2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, null))));
        addTwoNumbers(l1, l2);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode record = new ListNode();
        addNumber(l1, l2, record);
        return record;
    }

    public void addNumber(ListNode l1, ListNode l2, ListNode record) {
        if (l1 != null || l2 != null) {
            if (l1 == null) {
                l1 = new ListNode(0);
            }
            if (l2 == null) {
                l2 = new ListNode(0);
            }
            if (record == null) {
                record = new ListNode(0);
            }
            int test = record.val + l1.val + l2.val;
            record.val = (record.val + l1.val + l2.val) % 10;
            ListNode l11 = l1.next;
            ListNode l21 = l2.next;
            if (test > 9) {
                record.next = new ListNode(1, null);
            } else if (l11 != null || l21 != null) {
                record.next = new ListNode(0, null);
            } else if (l11 == null && l21 == null) {
                return;
            }
            addNumber(l11, l21, record.next);
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            char alpha = s.charAt(end);
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     *
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     * 提示：
     *
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * Related Topics
     * 哈希表
     * 字符串
     * 滑动窗口
     * @param s
     * @param start
     * @param index
     * @param max
     * @param map
     * @return
     */
    public static int lengthOfLongestSubstring(String s,int start,int index,int max,Map<String,Integer> map) {
        if (s.length()==0){
            return max;
        }
        index=0;
        String[] str=s.split("");
        int item=0;
        for (int i = start; i < str.length ; i++) {
            if (null!=map.get(str[i])){
                if (map.get(str[i])<index){
                    if (i-index+1>max){
                        max=i-index+1;
                    }
                    map.put(str[i],i);
                }else {
                    if (max<i-map.get(str[i])){
                        max=i-map.get(str[i]);
                    }
                    index=map.get(str[i])+1;
                    map.put(str[i],i);
                }

            }else {
                if (i-index+1>max){
                    max=i-index+1;
                }
                map.put(str[i],i);
            }
        }
        return max;
    }

    /**
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     *
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     *
     * 例如，121 是回文，而 123 不是。
     * 示例 1：
     *
     * 输入：x = 121
     * 输出：true
     * 示例 2：
     *
     * 输入：x = -121
     * 输出：false
     * 解释：从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     * 示例 3：
     *
     * 输入：x = 10
     * 输出：false
     * 解释：从右向左读, 为 01 。因此它不是一个回文数。
     * 提示：
     *
     * -231 <= x <= 231 - 1
     * 进阶：你能不将整数转为字符串来解决这个问题吗？
     */

    public boolean isPalindrome(int x) {
        if (x<0){
            return false;
        }
        String ss=String.valueOf(x);
        for (int i = 0; i <ss.length()/2; i++) {
            if (String.valueOf(ss.charAt(i)).equals(String.valueOf(ss.charAt(ss.length()-1-i)))){

            }else {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.printf("结果:"+romanToInt("III"));
    }

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做 XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。
     *
     * 示例 1:
     *
     * 输入: s = "III"
     * 输出: 3
     * 示例 2:
     *
     * 输入: s = "IV"
     * 输出: 4
     * 示例 3:
     *
     * 输入: s = "IX"
     * 输出: 9
     * 示例 4:
     *
     * 输入: s = "LVIII"
     * 输出: 58
     * 解释: L = 50, V= 5, III = 3.
     * 示例 5:
     *
     * 输入: s = "MCMXCIV"
     * 输出: 1994
     * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
     * 提示：
     *
     * 1 <= s.length <= 15
     * s 仅含字符 ('I', 'V', 'X', 'L', 'C', 'D', 'M')
     * 题目数据保证 s 是一个有效的罗马数字，且表示整数在范围 [1, 3999] 内
     * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
     * IL 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
     * 关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
     */
    public static int romanToInt(String s) {
        int getInt=0;
        if (s.indexOf("IV")>-1){
            getInt=getInt+4;
            s=s.replaceFirst("IV","");
        }
        if (s.indexOf("IX")>-1){
            getInt=getInt+9;
            s=s.replaceFirst("IX","");
        }
        if (s.indexOf("XL")>-1){
            getInt=getInt+40;
            s=s.replaceFirst("XL","");
        }
        if (s.indexOf("XC")>-1){
            getInt=getInt+90;
            s=s.replaceFirst("XC","");
        }
        if (s.indexOf("CD")>-1){
            getInt=getInt+400;
            s=s.replaceFirst("CD","");
        }
        if (s.indexOf("CM")>-1) {
            getInt = getInt + 900;
            s=s.replaceFirst("CM", "");
        }
        while (s.contains("I")){
            getInt = getInt + 1;
            s=s.replaceFirst("I", "/");
        }
        while (s.contains("V")){
            getInt = getInt + 5;
            s=s.replaceFirst("V", "/");
        }
        while (s.contains("X")){
            getInt = getInt + 10;
            s=s.replaceFirst("X", "/");
        }
        while (s.contains("L")){
            getInt = getInt + 50;
            s=s.replaceFirst("L", "/");
        }
        while (s.contains("C")){
            getInt = getInt + 100;
            s=s.replaceFirst("C", "/");
        }
        while (s.contains("D")){
            getInt = getInt + 500;
            s=s.replaceFirst("D", "/");
        }
        while (s.contains("M")){
            getInt = getInt + 1000;
            s=s.replaceFirst("M", "/");
        }
        return getInt;
    }
}

