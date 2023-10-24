### 递归面试题
> 编程题:有n步台阶，一次只能上1步或2步，共有多少种走法?

**1.首先从简答的分析。**

- 当 n=1时，只能走一步；当 n=2时，能走两步(一步一步走，一下走两步)

> 我们这个时候用n表示自变量，用函数F(n)表示n步台阶对应的几种走法。
那么我们就得出。F(1)=1; F(2)=2。

那么当n=3的时候，首先我们第一次可以选择走1步或2步。`如果选择走一步的话，那么剩下就是 2个台阶，是不是也就剩下
我们上面分析的F(2)了`。反之，我们如果选择第一次走两步，那么剩下的就只有一个台阶了，那就剩下的是F(1)。

#### 总结分析：
![img_2.png](Image%2Fimg_2.png)
![img_1.png](Image%2Fimg_1.png)
由上图很容易得出，这是个斐波那契数。代码实现的逻辑也是上面展示的效果。

#### 代码实现
[FibonacciSequence.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion01%2FFibonacciSequence.java)

```java
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
```
