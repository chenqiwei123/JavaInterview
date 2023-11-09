## 阻塞队列
### 概念

**队列**

> 队列就可以想成是一个数组，从一头进入，一头出去，排队买饭

**阻塞队列**

> BlockingQueue 阻塞队列，排队拥堵，首先它是一个队列，而一个阻塞队列在数据结构中所起的作用大致如下图所示：

![img_27.png](Image2%2Fimg_27.png)

**BlockingQueue阻塞队列是属于一个接口，底下有七个实现类**

- ArrayBlockingQueue: 基于数组实现的阻塞队列，FIFO
- LinkedBlockingQueue: 基于链表实现的阻塞队列，FIFO
- PriorityBlockingQueue: 基于优先级队列实现的阻塞队列，优先级高的先出
- SynchronousQueue: 同步队列，不存储元素，每个插入操作必须等待一个取出操作
- DelayQueue: 延迟队列，可以指定元素的延迟时间
- TransferQueue: 转移队列，可以将元素从一个队列转移到另一个队列


### 常见方法

- `int size()`: 返回队列中元素的个数
- `boolean isEmpty()`: 判断队列是否为空
- `boolean contains(Object o)`: 判断队列中是否包含元素o
- `boolean add(E e)`: 将元素e插入队列中
- `boolean remove(Object o)`: 从队列中移除元素o
- `boolean containsAll(Collection<?> c)`: 判断队列中是否包含集合c中的所有元素
- `boolean addAll(Collection<? extends E> c)`: 将集合c中的所有元素插入队列中
- `boolean removeAll(Collection<?> c)`: 从队列中移除集合c中的所有元素
- `boolean retainAll(Collection<?> c)`: 从队列中移除队列中不在集合c中的元素
- `void clear()`: 清空队列
- `E element()`: 返回队列中首个元素
- `E peek()`: 返回队列中首个元素，但不移除
- `E poll()`: 返回队列中首个元素，并移除

**BlockingQueue核心方法**

![img_28.png](Image2%2Fimg_28.png)


|方法| 说明                                        |
|----|-------------------------------------------|
|add(e)| 如果队列未满，则将元素插入队列，并返回true；如果队列满，则抛出IllegalStateException异常       |
|offer(e)| 如果队列未满，则将元素插入队列，并返回true；如果队列满，则返回false    |
|put(e)| 如果队列未满，则将元素插入队列，并返回true；如果队列满，则阻塞等待，直到队列可用 |
|remove(o)| 如果队列包含元素o，则将其移移除并返回true；如果队列不包含元素o，则返回false |
|poll(long timeout, TimeUnit unit)| 出队一个元素，如果存在则直接出队，如果没有空间则等待timeout时间，无元素则返回null |
|take()| 如果队列包含元素，则将其移除并返回；如果队列为空，则阻塞等待，直到队列可用     |

### Synchronized和Lock的区别

**前言**
> 早期的时候我们对线程的主要操作为：
> 
> synchronized wait notify

> 然后后面出现了替代方案
> 
> lock await signal

![img_29.png](Image2%2Fimg_29.png)

### 问题

**synchronized 和 lock 有什么区别？用新的lock有什么好处？举例说明**

| 区别     | synchronized | lock(ReentrantLock)                                                                                               |
|--------| ----------- |------------------------------------------------------------------------------------------------------|
| 概念     | JVM层面，属于java的关键字      | 具体类（`java.util.concurrent.locks.Lock`）是api层面的锁                                                       |
| 使用方法   | 不需要用户去手动释放锁，当synchronized代码执行后，系统会自动让线程释放对锁的占用     | 需要用户去手动释放锁，若没有主动释放锁，就有可能出现死锁的现象，需要lock() 和 unlock() 配置try catch语句来完成                                 |
| 等待是否中断    | 不可中断，除非抛出异常或者正常运行完成         | 可中断，可以设置超时方法设置超时方法，trylock(long timeout, TimeUnit unit)lockInterrupible() 放代码块中，调用interrupt() 方法可以中断 |
| 加锁是否公平    | 非公平锁    | 默认非公平锁，构造函数可以传递boolean值，true为公平锁，false为非公平锁                                                          |
| 锁绑定多个条件Condition    | 没有，要么随机，要么全部唤醒    | 用来实现分组唤醒需要唤醒的线程，可以精确唤醒                                                                               |

### 举例
针对刚刚提到的区别的第5条，我们有下面这样的一个场景
> 题目：多线程之间按顺序调用，实现 A-> B -> C 三个线程启动，要求如下：
> 
> AA打印5次，BB打印10次，CC打印15次
>
> 紧接着
> 
> AA打印5次，BB打印10次，CC打印15次
> 
> ..
> 
> 来10轮

我们会发现，这样的场景在使用synchronized来完成的话，会非常的困难，但是使用lock就非常方便了

也就是我们需要实现一个链式唤醒的操作

![img_30.png](Image2%2Fimg_30.png)

当A线程执行完后，B线程才能执行，然后B线程执行完成后，C线程才执行,然后D线程执行。

#### 代码实现

```java

/**
 * 题目：多线程之间按顺序调用，实现 A-> B -> C 三个线程启动，要求如下：
 * AA打印5次，BB打印10次，CC打印15次
 * 紧接着
 * AA打印5次，BB打印10次，CC打印15次
 * ..
 * 来10轮
 */
public class LockAwaitDemo {
    private String thisThreadName="A";
    private Lock lockDemo = new ReentrantLock();
    public Condition conditionA=lockDemo.newCondition();
    public Condition conditionB=lockDemo.newCondition();
    public Condition conditionC=lockDemo.newCondition();

    public static void main(String[] args) {
        LockAwaitDemo lockAwaitDemo = new LockAwaitDemo();
         for(int i=0;i<10;i++) {
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"A");
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"B");
            lockAwaitDemo.getThreadRun(lockAwaitDemo,"C");
        }
    }

    public void getThreadRun(LockAwaitDemo lockAwaitDemo,String threadName){
        new Thread(()->{
            lockDemo.lock();
            while (!lockAwaitDemo.thisThreadName.equals(threadName)){
                try {
                    if ("A".equals(threadName)){
                        conditionA.await();
                    }
                    if ("B".equals(threadName)){
                        conditionB.await();
                    }
                    if ("C".equals(threadName)){
                        conditionC.await();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("A".equals(threadName)){
                System.out.println("---------------开始----------------");
                lockAwaitDemo.thisThreadName="B";
                lockAwaitDemo.print5(5);
                conditionB.signal();
            }
            if ("B".equals(threadName)){
                lockAwaitDemo.thisThreadName="C";
                lockAwaitDemo.print5(10);
                conditionC.signal();
            }
            if ("C".equals(threadName)){
                lockAwaitDemo.thisThreadName="A";
                lockAwaitDemo.print5(15);
                conditionA.signal();
                System.out.println("---------------结束----------------");
            }
            lockDemo.unlock();
        },threadName).start();
    }

    public void print5(int number){
        for (int j=1;j<=number;j++) {
            System.out.println(Thread.currentThread().getName()+"\t "+j);
        }
    }

}

```
