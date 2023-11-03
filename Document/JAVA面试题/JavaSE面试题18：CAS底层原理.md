### CAS底层原理

#### 概念

CAS（Compare And Swap）是一种无锁算法，它允许一个变量在多个线程中同时进行比较并交换。

CAS操作包含三个操作数：

- 内存位置（V）
- 预期原值（A）
- 新值（B）

当且仅当预期原值A和内存位置V的值相同时，将内存位置V的值修改为新值B。否则，不会执行任何操作。

#### 为什么会引进CAS

可以考虑参考：[JavaSE面试题16：Volatile不保证原子性](JavaSE%C3%E6%CA%D4%CC%E216%A3%BAVolatile%B2%BB%B1%A3%D6%A4%D4%AD%D7%D3%D0%D4.md)。

为什么会出现和期望值不一样，
就是多个线程对同一变量进行增加，多个线程都将同一变量进行自增操作后，准备写进主内存，但是总线嗅探还未及时更新缓存通知其他线程，这个时候一个线程就当时拷贝的i=5自增刷新到i=6了。
但另一个线程正准备也写进去，但是这个线程拷贝的也是之前的i=5，所以就出现了不一致。两次进行自增了，但是只增加一次了。这个时候就引入CAS(Compare And Swap)了，每次刷新到主内存的时候，判断主内存的这个变量
的值是不是我当时拷贝的这个值，是的话，那么我就进行更新，如果不是我就重新拷贝这个值，重新进行一遍操作。

#### CAS
CAS是为了解决多线程并发问题而产生的，在并发编程中，CAS是解决数据不一致问题的关键。

CAS的原理是：当多个线程同时操作一个变量时，如果某个线程在操作时发现变量的值有发生变化，那么它会立即停止，

并将内存中该变量的旧值记录下来，然后在以后某个时刻，用这个旧值来检查该变量是否被修改。


> CAS并发原语体现在Java语言中就是`sun.misc.Unsafe`类的各个方法。
> 调用`UnSafe`类中的CAS方法，JVM会帮我们实现出CAS汇编指令，这是一种完全依赖于硬件的功能，
> 通过它实现了原子操作，再次强调，由于CAS是一种系统原语，
> 原语属于操作系统用于范畴，是由若干条指令组成，用于完成某个功能的一个过程，
> 并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，
> 不会造成所谓的数据不一致的问题，也就是说CAS是线程安全的。

#### 代码使用

首先调用AtomicInteger创建了一个实例， 并初始化为5

>  // 创建一个原子类
>  AtomicInteger atomicInteger = new AtomicInteger(5);

然后调用CAS方法，企图更新成2019，这里有两个参数，一个是5，表示期望值，第二个就是我们要更新的值

> atomicInteger.compareAndSet(5, 2019)

然后再次使用了一个方法，同样将值改成1024

atomicInteger.compareAndSet(5, 1024)

**完整代码如下：**
```java
/**
 * CASDemo
 *
 * 比较并交换：compareAndSet
 *
 * @author: 陌溪
 * @create: 2020-03-10-19:46
 */
public class CASDemo {
    public static void main(String[] args) {
        // 创建一个原子类
        AtomicInteger atomicInteger = new AtomicInteger(5);

        /**
         * 一个是期望值，一个是更新值，但期望值和原来的值相同时，才能够更改
         * 假设三秒前，我拿的是5，也就是expect为5，然后我需要更新成 2019
         */
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());
    }
}
```
上面代码的执行结果为: 
![img_12.png](Image2%2Fimg_12.png)

这是因为我们执行第一个的时候，期望值和原本值是满足的，因此修改成功，
但是第二次后，主内存的值已经修改成了2019，不满足期望值，因此返回了false，本次写入失败。

![img_13.png](Image2%2Fimg_13.png)

#### 代码实现自旋锁

[CASDemo.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FCASDemo.java)

```java
public class CASDemo {
    public volatile Integer indexNumber=0;

     /**
      * 利用CAS的原理，自己制作一个自旋锁
      * @param args
      */
    public static void main(String[] args) {
//        CASLeadInto();//引入CAS的原因，为了解决这类问题。
        CASDemo casDemo=new CASDemo();
        Lock l = new ReentrantLock();
        for(int i=0;i<10;i++){
            new Thread(()->{
                for(int j=0;j<10000;j++) {
                    Integer temp;
                    Boolean flag=false;
                    do{
                        temp=casDemo.indexNumber;
                        if(temp.equals(casDemo.indexNumber)){
                            l.lock();
                                if(temp.equals(casDemo.indexNumber)) {
                                    casDemo.indexNumber = casDemo.indexNumber+1;
                                    flag = true;
                                }
                            l.unlock();
                        }

                    }while(!flag);
                }
            }).start();
        }
        while (Thread.activeCount()>2){

        }
        // 如何解决。可以添加synchronized、atomicInteger.getAndIncrement()、atomicInteger.getAndDecrement()实现原子性。
        System.out.println(casDemo.indexNumber);
    }
  }
```

**1、atomicInteger.getAndIncrement()方法的源码**

![img_14.png](Image2%2Fimg_14.png)

从这里能够看到，底层又调用了一个unsafe类的getAndAddInt方法

![img_15.png](Image2%2Fimg_15.png)

Unsafe是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地（Native）方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定的内存数据。Unsafe类存在sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因为Java中的CAS操作的执行依赖于Unsafe类的方法。

注意Unsafe类的所有方法都是native修饰的，也就是说unsafe类中的方法都直接调用操作系统底层资源执行相应的任务

为什么Atomic修饰的包装类，能够保证原子性，依靠的就是底层的unsafe类

**2、变量valueOffset**
表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的。

![img_16.png](Image2%2Fimg_16.png)

从这里我们能够看到，通过valueOffset，直接通过内存地址，获取到值，然后进行加1的操作

**3、变量value用volatile修饰**
保证了多线程之间的内存可见性

![img_17.png](Image2%2Fimg_17.png)

**var5:** 就是我们从主内存中拷贝到工作内存中的值(每次都要从主内存拿到最新的值到自己的本地内存，然后执行compareAndSwapInt()在再和主内存的值进行比较。因为线程不可以直接越过高速缓存，直接操作主内存，所以执行上述方法需要比较一次，在执行加1操作)

那么操作的时候，需要比较工作内存中的值，和主内存中的值进行比较

假设执行 compareAndSwapInt返回false，那么就一直执行 while方法，直到期望的值和真实值一样

- val1：AtomicInteger对象本身
- var2：该对象值得引用地址
- var4：需要变动的数量
- var5：用var1和var2找到的内存中的真实值

用该对象当前的值与var5比较
如果相同，更新var5 + var4 并返回true
如果不同，继续取值然后再比较，直到更新完成
这里没有用synchronized，而用CAS，这样提高了并发性，也能够实现一致性，是因为每个线程进来后，进入的do while循环，然后不断的获取内存中的值，判断是否为最新，然后在进行更新操作。

假设线程A和线程B同时执行getAndInt操作（分别跑在不同的CPU上）

- AtomicInteger里面的value原始值为3，即主内存中AtomicInteger的 value 为3，根据JMM模型，线程A和线程B各自持有一份价值为3的副本，分别存储在各自的工作内存
- 线程A通过getIntVolatile(var1 , var2) 拿到value值3，这是线程A被挂起（该线程失去CPU执行权）
- 线程B也通过getIntVolatile(var1, var2)方法获取到value值也是3，此时刚好线程B没有被挂起，并执行了compareAndSwapInt方法，比较内存的值也是3，成功修改内存值为4，线程B打完收工，一切OK
- 这是线程A恢复，执行CAS方法，比较发现自己手里的数字3和主内存中的数字4不一致，说明该值已经被其它线程抢先一步修改过了，那么A线程本次修改失败，只能够重新读取后在来一遍了，也就是在执行do while
- 线程A重新获取value值，因为变量value被volatile修饰，所以其它线程对它的修改，线程A总能够看到，线程A继续执行compareAndSwapInt进行比较替换，直到成功。

> Unsafe类 + CAS思想： 也就是自旋，自我旋转

#### CAS的缺点

- CAS长时间不成立会一直占用CPU，导致CPU资源被浪费。
- 只能保证一个共享变量的原子操作
- 引进来ABA的问题

#### 什么是ABA问题

> ABA问题是指在一个变量上做了两次操作，第一次操作是将变量A的值设为B，第二次操作是将变量A的值又恢复为A。

#### ABA问题如何解决

> 
> 
> 解决ABA问题的关键是使用版本号，在变量A上记录一个版本号，每次操作时，将版本号加一，当A值恢复为B时，检查A是否等于B，如果相等则说明A没有被其他线程修改过，可以执行操作，否则说明A被其他线程修改过。


#### 代码层次解释

[ABASolve.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FABASolve.java)

- **ABA问题产生**

```java
    /**
     * ABA问题的解决，添加版本号。AtomicStampedReference进行解决
     * A线程已经将变量A的值从100改为101，版本号+1，又改回了100版本号再次+1。B线程将变量A的值试图将100，发现版本号不一样，修改不成功。
     */
    public void ABAProblemSolve(){
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicStampedReference.compareAndSet(100,101,1,2);
            ABASolve.atomicStampedReference.compareAndSet(101,100,2,3);

        },"A").start();
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicStampedReference.compareAndSet(101,102,1,2);
        },"B").start();
        while (Thread.activeCount()>2){

        }
        System.out.println(ABASolve.atomicStampedReference.getReference());
    }
```

- **ABA问题解决**

```java
    /**
     * ABA问题的产生
     * A线程已经将变量A的值从100改为101，又改回了100。但是B线程也能将变量A的值从100(A现成修改后的100了)改为102，
     */
    public void ABAProblem(){
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicInteger.compareAndSet(100,101);
            ABASolve.atomicInteger.compareAndSet(101,100);

        },"A").start();
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ABASolve.atomicInteger.compareAndSet(100,102);
        },"B").start();
        while (Thread.activeCount()>2){

        }
        System.out.println(ABASolve.atomicInteger);
    }
```
