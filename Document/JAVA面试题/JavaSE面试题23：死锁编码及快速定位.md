## 死锁编码及定位分析
死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。

![img_34.png](Image2%2Fimg_34.png)

### 死锁产生的四个必要条件

**互斥**

> 解决方法：把互斥的共享资源封装成可同时访问

**占有且等待**

> 解决方法：进程请求资源时，要求它不占有任何其它资源，也就是它必须一次性申请到所有的资源，这种方式会导致资源效率低。

**非抢占式**

> 解决方法：如果进程不能立即分配资源，要求它不占有任何其他资源，也就是只能够同时获得所有需要资源时，才执行分配操作

**循环等待**

> 解决方法：对资源进行排序，要求进程按顺序请求资源。

### 死锁的编码

[DeadLockDemo.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FDeadLockDemo.java)

```java
package com.example.javainterview.Version02;

import lombok.Data;

@Data
class DeadLockEntity{
    private String lockA="LOCK_A";
    private String lockB="LOCK_B";

    public DeadLockEntity(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
}
public class DeadLockDemo {
    private static String lockA="LOCK_A";
    private static String lockB="LOCK_B";
    public void DeaDLockRun(String lockA,String lockB){
        synchronized (lockA){
            System.out.println("LOCK_A========================");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lockB){
                System.out.println("LOCK_B========================");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            new DeadLockDemo().DeaDLockRun(lockA,lockB);
        }).start();
        new Thread(()->{
            new DeadLockDemo().DeaDLockRun(lockB,lockA);
        }).start();
    }
}

```

**运行结果，main线程无法结束**

![img_35.png](Image2%2Fimg_35.png)

**排查死锁**

当我们出现死锁的时候，首先需要使用jps命令查看运行的程序(关注我们的业务线程包名)

```bash
jps -l
```

![img_36.png](Image2%2Fimg_36.png)

然后利用jstack命令查看线程栈信息

```bash
jstack 进程号
```


![img_37.png](Image2%2Fimg_37.png)

可以看到，线程A和线程B都在等待对方的锁，这就是死锁。---> 线上分布式的话，依次重启服务，直到死锁消失。

