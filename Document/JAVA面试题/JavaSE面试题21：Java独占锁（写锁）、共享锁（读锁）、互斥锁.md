## 独占锁（写锁） / 共享锁（读锁） / 互斥锁

**概念**

> 独占锁：指该锁一次只能被一个线程所持有。对ReentrantLock和Synchronized而言都是独占锁
> 
> 共享锁：指该锁可以被多个线程锁持有

**对ReentrantReadWriteLock其读锁是共享，其写锁是独占**

写的时候只能一个人写，但是读的时候，可以多个人同时读

为什么会有写锁和读锁
原来我们使用ReentrantLock创建锁的时候，是独占锁，也就是说一次只能一个线程访问，但是有一个读写分离场景，读的时候想同时进行，因此原来独占锁的并发性就没这么好了，因为读锁并不会造成数据不一致的问题，因此可以多个人共享读

### 为什么会有写锁和读锁
原来我们使用ReentrantLock创建锁的时候，是独占锁，也就是说一次只能一个线程访问，但是有一个读写分离场景，读的时候想同时进行，因此原来独占锁的并发性就没这么好了，因为读锁并不会造成数据不一致的问题，因此可以多个人共享读

> 多个线程 同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行，
> 但是如果一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读或写。

#### ReadWriteLock实现写锁独占，读锁共享

```java

/**
 * Created by riven_chris on 15/7/2.
 * 读-读：能共存
 *
 * 读-写：不能共存
 *
 * 写-写：不能共存
 */
class MyCache{
    private volatile Map<String, Object> cache = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();//独占写，共享读

    public static void main(String[] args) {
        MyCache myCache=new MyCache();
        for (int j=0;j<3;j++){
            new Thread(()->{
                myCache.put("cqw", "sm");
            }).start();
        }
        for (int i=0;i<3;i++){
            new Thread(()->{
                myCache.get("cqw");
            }).start();
        }
    }

    public void put(String key, Object value){
        readWriteLock.writeLock().lock();//独占写
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入数据，独占锁。。");
            cache.put(key, value);
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            readWriteLock.writeLock().unlock();//释放写锁
            System.out.println(Thread.currentThread().getName()+"\t 正在释放写入的锁。。");
        }
    }

    public void get(final String key){
        System.out.println(Thread.currentThread().getName()+"\t 正在读取数据。。");
        System.out.println("读取出来的数据：Value="+cache.get(key));
    }
}

```

#### 运行结果

![img_25.png](Image2%2Fimg_25.png)

从运行结果我们可以看出，写入操作是一个一个线程进行执行的，并且中间不会被打断，而读操作的时候，是同时5个线程进入，然后并发读取操作


##### 附加

**countdownLatch**

> 计数器：在计数器上调用await()方法，当前线程将被挂起，直到计数器的值变为0才会被唤醒。
> 
> 计数器：在计数器上调用countDown()方法，计数器的值将会减1，当计数器的值变为0时，
> 所有调用await()方法的线程将被唤。

**countdownLatch的代码示例**

```java
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName()+"\t 进来了！！！");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 我走了！！");
                countDownLatch.countDown();
            }).start();
        }
        // 计数器为0, 等待所有线程执行完毕
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t "+"所有线程都执行完毕了！！");
    }
}
```

**Semaphore**

> 
> 信号量：Semaphore 是一个计数信号量，用于控制同时访问特定资源的线程数量。

**Semaphore的代码示例**

```java
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore =new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t 进来了！！！");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t 我走了！！");
                semaphore.release();
            }).start();
        }
        while (Thread.activeCount()>2) {

        }
    }
}
```

**运行结果**

![img_26.png](Image2%2Fimg_26.png)
