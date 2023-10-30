### 谈谈你对volatile的理解

说到JVM内存模型，我们就需要了解JMM内存模型。JMM内存模型是Java内存模型的子集，
它定义了Java内存模型的一些规则，并提供了一套原子性、可见性、有序性的规则。

**JMM关于同步的规定：**
> 1. 线程解锁前，必须把共享变量的最新值刷新到主内存中。
> 
> 3. 线程加锁时，如果发现共享变量的值没有在主内存中，或者当前线程无法获得锁，就阻塞等待，直到获得锁。
> 
>4. 线程在释放锁时，如果发现共享变量的值已经发生了变化，那么必须把共享变量的最新值从主内存刷新到线程的工作内存中。

每个Java线程都有自己的工作内存。操作数据，首先从主内存中读，得到一份拷贝，操作完毕后再写回到主内存。

![img.png](Image2%2Fimg.png)

**JMM可能带来可见性、原子性和有序性问题。**
- **可见性：** 就是某个线程对主内存内容的更改，应该立刻通知到其它线程。
- **原子性：** 是指一个操作是不可分割的，不能执行到一半，就不执行了。
- **有序性：** 就是指令是有序的，不会被重排。

由于JVM运行程序的实体是线程，而每个线程创建时JVM都会为其创建一个工作内存（有些地方称为栈空间），
工作内存是每个线程的私有数据区域，而Java内存模型中规定所有变量都存储在主内存，
主内存是共享内存区域，所有线程都可以访问，但线程对变量的操作（读取赋值等）必须在工作内存中进行，
首先要将变量从主内存拷贝到自己的工作内存空间，然后对变量进行操作，操作完成后再将变量写会主内存，
不能直接操作主内存中的变量，各个线程中的工作内存中存储着主内存中的变量副本拷贝，
因此不同的线程间无法访问对方的工作内存，线程间的通信（传值）必须通过主内存来完成，其简要访问过程：

![img_1.png](Image2%2Fimg_1.png)

数据传输速率：硬盘 < 内存 < < cache < CPU

上面提到了两个概念：主内存 和 工作内存

- 主内存：就是计算机的内存，也就是经常提到的8G内存，16G内存

- 工作内存：但我们实例化 new student，那么 age = 25 也是存储在主内存中

- 当同时有三个线程同时访问 student中的age变量时，那么每个线程都会拷贝一份，到各自的工作内存，从而实现了变量的拷贝

![img_2.png](Image2%2Fimg_2.png)

即：JMM内存模型的可见性，指的是当主内存区域中的值被某个线程写入更改后，其它线程会马上知晓更改后的值，并重新得到更改后的值。

#### 可见性的验证
[VolatileDemo.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FVolatileDemo.java)

```java
public class VolatileDemo {
    public static void main(String[] args) {
        // 可见性的代码验证，正常情况。多个线程拿到主内存的同一变量数据，某个线程修改了这个变量数值，那么其他的变量是没有感知的。
        ObjectDemo objectDemo =new ObjectDemo();
        Thread t1=new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            objectDemo.addNumber();
        });
        t1.start();//t1线程启动，main线程将ints[0]变量拷贝主内存。t1线程修改了主内存的ints[0]，
        // t1线程退出，判断这个时候的main线程是否感知到这个变量已经修改了。
        // 1.线程之间的不可见性。
//        while (objectDemo.a==1&&t1.isAlive()){// t1.isAlive()判断这个线程是否存活，可以去掉查看，就是个死循环了。
//            // 这个时候执行这个循环的时候的objectDemo.a=1保存在缓存中，每次拿的都是缓存中的数据
//            System.out.println(Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
//        }
        // 在这一步是进行重新拿主内存的共享变量拷贝一份在工作内存
        System.out.println(Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
        // 2. Volatile的可见性
        while (objectDemo.a==1){// t1.isAlive()判断这个线程是否存活，可以去掉查看。这个时候执行这个循环的时候的objectDemo.a=1保存在缓存中，每次拿的都是缓存中的数据

        }
        System.out.println("Volatile的可见性:"+Thread.currentThread().getName()+"\t objectDemo"+objectDemo.a);
    }
}

class ObjectDemo {
//    public int a=1; //不可见性
    public volatile int a=1; // Volatile的可见性
    public void addNumber(){
        this.a=60;
    }

    @Override
    public String toString() {
        return "ObjectDemo{" +
                "a=" + a +
                '}';
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}


```
