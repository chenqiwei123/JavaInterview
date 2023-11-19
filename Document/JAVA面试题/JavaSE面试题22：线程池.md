## 线程池

### 前言
> 获取多线程的方法，我们都知道有三种，还有一种是实现Callable接口

- 实现Runnable接口
- 实现Callable接口
- 实例化Thread类
- 使用线程池获取

### Callable接口

Callable接口，是一种让线程执行完成后，能够返回结果的

在说到Callable接口的时候，我们不得不提到Runnable接口

```java
/**
 * 实现Runnable接口
 */
class MyThread implements Runnable {

    @Override
    public void run() {

    }
}
```

我们知道，实现Runnable接口的时候，需要重写run方法，也就是线程在启动的时候，会自动调用的方法

同理，我们实现Callable接口，也需要实现call方法，但是这个时候我们还需要有返回值，这个Callable接口的应用场景一般就在于批处理业务，比如转账的时候，需要给一会返回结果的状态码回来，代表本次操作成功还是失败

```java
/**
 * 实现Callable接口,有返回值，例如返回支付的状态  
 */
class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 100;
    }
}
```
**介绍FutureTask**

最后我们需要做的就是通过Thread线程， 将MyThread2实现Callable接口的类包装起来

这里需要用到的是FutureTask类，他实现了Runnable接口，并且还需要传递一个实现Callable接口的类作为构造函数

```java
// FutureTask：实现了Runnable接口，构造函数又需要传入 Callable接口
// 这里通过了FutureTask接触了Callable接口
FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
```

最后通过 futureTask.get() 获取到返回值

```java
// 输出FutureTask的返回值
System.out.println("result FutureTask " + futureTask.get());
```

这就相当于原来我们的方式是main方法一条龙之心，后面在引入Callable后，对于执行比较久的线程，可以单独新开一个线程进行执行，最后在进行汇总输出

> 最后需要注意的是 要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致阻塞，直到计算完成

![img_31.png](Image2%2Fimg_31.png)

也就是说 futureTask.get() 需要放在最后执行，这样不会导致主线程阻塞

也可以使用下面算法，使用类似于自旋锁的方式来进行判断是否运行完毕

```java
// 判断futureTask是否计算完成
while(!futureTask.isDone()) {

}
```

**注意**
多个线程执行 一个FutureTask的时候，只会计算一次

```java
FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());

// 开启两个线程计算futureTask
new Thread(futureTask, "AAA").start();
new Thread(futureTask, "BBB").start();
```

如果我们要两个线程同时计算任务的话，那么需要这样写，需要定义两个futureTask

```java
FutureTask<Integer> futureTask = new FutureTask<>(new MyThread2());
FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread2());

// 开启两个线程计算futureTask
new Thread(futureTask, "AAA").start();

new Thread(futureTask2, "BBB").start();
```

## ThreadPoolExecutor

#### 为什么用线程池

线程池做的主要工作就是控制运行的线程的数量，处理过程中，将任务放入到队列中，然后线程创建后，启动这些任务，如果线程数量超过了最大数量的线程排队等候，等其它线程执行完毕，再从队列中取出任务来执行。

它的主要特点为：

- 线程复用
- 控制最大并发数
- 管理线程

线程池中的任务是放入到阻塞队列中的

**创建线程池**

> Executors.newFixedThreadPool(int i) ：创建一个拥有 i 个线程的线程池
- 执行长期的任务，性能好很多
- 创建一个定长线程池，可控制线程数最大并发数，超出的线程会在队列中等待

> Executors.newSingleThreadExecutor：创建一个只有1个线程的 单线程池
- 一个任务一个任务执行的场景
- 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行

> Executors.newCacheThreadPool(); 创建一个可扩容的线程池
- 执行很多短期异步的小程序或者负载教轻的服务器
- 创建一个可缓存线程池，如果线程长度超过处理需要，可灵活回收空闲线程，如无可回收，则新建新线程

> Executors.newScheduledThreadPool(int corePoolSize)：线程池支持定时以及周期性执行任务，创建一个corePoolSize为传入参数，最大线程数为整形的最大数的线程池

![img_32.png](Image2%2Fimg_32.png)

**线程池在创建的时候，一共有7大参数**

- corePoolSize：核心线程数，当线程池中的线程数小于corePoolSize时，就会创建新的线程来执行任务
- maximumPoolSize：最大线程数，当线程池中的线程数大于maximumPoolSize时，就会创建新的线程来执行任务
- keepAliveTime：线程池中超过corePoolSize数目的空闲线程最大存活时间，超过这个时间就会被回收
- TimeUnit：keepAliveTime的时间单位
- workQueue：阻塞队列，用来存储等待执行的任务
- threadFactory：线程工厂，用来创建线程 
- handler：拒绝策略，当队列满了，并且线程池也满了，那么就会执行拒绝策略

### 线程池底层工作原理
#### 线程池运行架构图
   
![img_33.png](Image2%2Fimg_33.png)

**文字说明**

1.在创建了线程池后，等待提交过来的任务请求

2.当调用execute()方法添加一个请求任务时，线程池会做出如下判断

- 如果正在运行的线程池数量小于corePoolSize，那么马上创建线程运行这个任务
- 如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列
- 如果这时候队列满了，并且正在运行的线程数量还小于maximumPoolSize，那么还是创建非核心线程去运行这个任务；
- 如果队列满了并且正在运行的线程数量大于或等于maximumPoolSize，那么线程池会启动饱和拒绝策略来执行

3.当一个线程完成任务时，它会从队列中取下一个任务来执行

4.当一个线程无事可做操作一定的时间(keepAliveTime)时，线程池会判断：

- 如果当前运行的线程数大于corePoolSize，那么这个线程就被停掉
- 所以线程池的所有任务完成后，它会最终收缩到corePoolSize的大小

以顾客去银行办理业务为例，谈谈线程池的底层工作原理

- 最开始假设来了两个顾客，因为corePoolSize为2，因此这两个顾客直接能够去窗口办理
- 后面又来了三个顾客，因为corePool已经被顾客占用了，因此只有去候客区，也就是阻塞队列中等待
- 后面的人又陆陆续续来了，候客区可能不够用了，因此需要申请增加处理请求的窗口，这里的窗口指的是线程池中的线程数，以此来解决线程不够用的问题
- 假设受理窗口已经达到最大数，并且请求数还是不断递增，此时候客区和线程池都已经满了，为了防止大量请求冲垮线程池，已经需要开启拒绝策略
- 临时增加的线程会因为超过了最大存活时间，就会销毁，最后从最大数削减到核心数

#### 线程池的合理参数

生产环境中如何配置 corePoolSize 和 maximumPoolSize

这个是根据具体业务来配置的，分为`CPU密集型`和`IO密集型`

**CPU密集型**

CPU密集的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行

CPU密集任务只有在真正的多核CPU上才可能得到加速（通过多线程）

而在单核CPU上，无论你开几个模拟的多线程该任务都不可能得到加速，因为CPU总的运算能力就那些

CPU密集型任务配置尽可能少的线程数量：

一般公式：CPU核数 + 1个线程数

**IO密集型**

由于IO密集型任务线程并不是一直在执行任务，则可能多的线程，如 CPU核数 * 2

IO密集型，即该任务需要大量的IO操作，即大量的阻塞

在单线程上运行IO密集型的任务会导致浪费大量的CPU运算能力花费在等待上

所以IO密集型任务中使用多线程可以大大的加速程序的运行，即使在单核CPU上，这种加速主要就是利用了被浪费掉的阻塞时间。

IO密集时，大部分线程都被阻塞，故需要多配置线程数：

参考公式：CPU核数 / (1 - 阻塞系数) 阻塞系数在0.8 ~ 0.9左右

例如：8核CPU：8/ (1 - 0.9) = 80个线程数
