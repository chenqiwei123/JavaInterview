package com.example.javainterview.Version01;

public class FibonacciSequence {
    /**
     *
     * @param n n步台阶
     * @return 共有多少种走法?
     */
    public static int getNumbers(int n){
        if (n<1){
            throw new IllegalArgumentException(n+"不能小于1啊，大哥！");
        }
        if (n==1||n==2){
            return n;
        }
        return getNumbers(n-1)+getNumbers(n-2);
    }

    public static void main(String[] args) {
        System.out.println("getNumbers(3):"+getNumbers(3));
        System.out.println("getNumbers(4):"+getNumbers(4));
        System.out.println("getNumbers(8):"+getNumbers(8));
        System.out.println("getNumbers(9):"+getNumbers(9));
    }
}
