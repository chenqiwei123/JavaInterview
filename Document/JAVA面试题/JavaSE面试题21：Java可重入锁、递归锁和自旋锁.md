## 可重入锁和递归锁ReentrantLock

**概念**

> 可重入锁就是递归锁
> 
> 指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码，在同一线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
> 
> 也就是说：线程可以进入任何一个它已经拥有的锁所同步的代码块
> 
> ReentrantLock / Synchronized 就是一个典型的可重入锁

可重入锁就是，在一个method1方法中加入一把锁，方法2也加锁了，那么他们拥有的是同一把锁。

**代码**

[ReentrantLockDemo.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FReentrantLockDemo.java)

```java
public class ReentrantLockDemo {
    private ReentrantLock lock = new ReentrantLock();

    public void method1() {
        lock.lock();
        try {
            System.out.println("method1");
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("method2");
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {}
}
```

**运行结果：**

![img_22.png](Image2%2Fimg_22.png)


#### 当我们在getLock方法加两把锁会是什么情况呢？ (阿里面试)
在getLock方法加两把锁会是什么情况呢？

```java
public void getLock() {
    lock.lock();
    try {
        System.out.println("getLock");
        lock.lock();
        try {
            System.out.println("getLock2");
        } finally {
            lock.unlock();
        }
    } finally {
        lock.unlock();
    }
}
```

运行结果：

![img_23.png](Image2%2Fimg_23.png)


#### 自旋锁

通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒，B随后进来发现当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到

[JavaSE面试题18：CAS底层原理.md](JavaSE%C3%E6%CA%D4%CC%E218%A3%BACAS%B5%D7%B2%E3%D4%AD%C0%ED.md)

**手写实现自旋锁代码**

> 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒，B随后进来发现当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到

```java

public class SpinLockDemo {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    public void myLock() {
        System.out.println(Thread.currentThread().getName()+"\t myLock");
        Thread current = Thread.currentThread();
        while (!owner.compareAndSet(null, current)) {
            // 自旋
        }
    }

    public void unMyLock(){
        System.out.println(Thread.currentThread().getName()+"\t unMyLock");
        owner.compareAndSet(Thread.currentThread(), null);
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo =new SpinLockDemo();
        new Thread(()->{
            spinLockDemo.myLock();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            spinLockDemo.unMyLock();
        },"AAAAA").start();
        new Thread(()->{
            spinLockDemo.myLock();
            spinLockDemo.unMyLock();
        },"BBBBB").start();
    }
}
```
