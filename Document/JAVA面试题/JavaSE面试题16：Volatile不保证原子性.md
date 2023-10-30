### Volatile不保证原子性
##### 前言
> 通过前面对JMM的介绍，我们知道，各个线程对主内存中共享变量的操作都是各个线程各自拷贝到自己的工作内存进行操作后在写回到主内存中的。
> 
> 这就可能存在一个线程AAA修改了共享变量X的值，但是还未写入主内存时，另外一个线程BBB又对主内存中同一共享变量X进行操作，但此时A线程工作内存中共享变量X对线程B来说是不可见，这种工作内存与主内存同步延迟现象就造成了可见性问题。

**原子性保证的是什么**

> 原子性是指不可分割的最小单位，比如一个操作是不可分割的，那么这个操作就是原子性的。
> 
> 举个例子，比如说，你要给一个变量加1，那么这个操作就是原子性的，因为这个操作是不可分割的，那么你要是分割开来，那么这个操作就不是原子性的。

**Volatile不保证原子性的代码验证**

[VolatileAtom.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FVolatileAtom.java)
```java

public class VolatileAtom {
    volatile int b = 0;
    public static void main(String[] args) {
        VolatileAtom atom = new VolatileAtom();
        Thread t1=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.b++;
            }
        });
        t1.start();
        Thread t2=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.b++;
            }
        });
        t2.start();
        try {
            t1.join(); //Waits for t1 thread to die
            t2.join();//Waits for t2 thread to die
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 我们按预期应该打印出2000000，但是为什么只打印出了  main 	 atom.b: 	 886597。每次执行效果还不一样呢。这就是volatile的不保证可见性，还不太明白看我下面娓娓道来。
        System.out.println(Thread.currentThread().getName()+" \t atom.b: \t "+atom.b);// main 	 atom.b: 	 886597

    }
```

***执行结果**

预期结果：main 	 atom.b: 	 2000000

第一次：
![img_3.png](Image2%2Fimg_3.png)
第二次：
![img_4.png](Image2%2Fimg_4.png)
第三次：
![img_5.png](Image2%2Fimg_5.png)

#### 为什么出现数值丢失

![img_6.png](Image2%2Fimg_6.png)

> 假设我们没有加 synchronized那么第一步就可能存在着，三个线程同时通过getfield命令，
> 拿到主存中的 n值，然后三个线程，各自在自己的工作内存中进行加1操作，
> 但他们并发进行 iadd 命令的时候，因为只能一个进行写，所以其它操作会被挂起，
> 假设1线程，先进行了写操作，在写完后，volatile的可见性，应该需要告诉其它两个线程，
> 主内存的值已经被修改了，但是因为太快了，其它两个线程，陆续执行 iadd命令，进行写入操作，
> 这就造成了其他线程没有接受到主内存n的改变，从而覆盖了原来的值，出现写丢失，
> 这样也就让最终的结果少于2000000

可以查看字节码文件

[ByteCodeRead.md](./附件/ByteCodeRead.md)

#### 如何解决

- 方法一：对变量进行增加 `synchronized`

![img_7.png](Image2%2Fimg_7.png)

- 方法二：使用原子类
- 
AtomicInteger atomicInteger =new AtomicInteger(); // 创建原子Integer的包装类，默认为0;

```java
public class VolatileAtom {
    int b = 0;
    public synchronized void Add(){
        this.b++;
    }
    public static void main(String[] args) {
        VolatileAtom atom = new VolatileAtom();
        AtomicInteger atomicInteger =new AtomicInteger(); // 创建原子Integer的包装类，默认为0;
        Thread t1=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.Add();
                atomicInteger.getAndIncrement();
            }
        });
        t1.start();
        Thread t2=new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                atom.Add();
                atomicInteger.getAndIncrement();
            }
        });
        t2.start();
        try {
            t1.join(); //Waits for t1 thread to die
            t2.join();//Waits for t2 thread to die
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 我们按预期应该打印出2000000，但是为什么只打印出了  main 	 atom.b: 	 886597。每次执行效果还不一样呢。这就是volatile的不保证可见性，还不太明白看我下面娓娓道来。
        System.out.println(Thread.currentThread().getName()+" \t atom.b: \t "+atom.b);// main 	 atom.b: 	 886597
        System.out.println(Thread.currentThread().getName()+" \t atomicInteger: \t "+atomicInteger);// main 	 atom.b: 	 886597

    }
```

![img_8.png](Image2%2Fimg_8.png)

