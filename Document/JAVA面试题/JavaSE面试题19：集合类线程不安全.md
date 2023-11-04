## 集合类不安全之并发修改异常

### 问题描述

在Java中，集合类是线程不安全的，如果多个线程同时对集合类进行操作，可能会导致并发修改异常。


#### 代码举例
[CollectionDemo.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion02%2FCollectionDemo.java)

```java
import java.util.ArrayList;
public class CollectionDemo {
    private static ArrayList<Integer> list = new ArrayList<>();
    public static void main(String[] args) {
        //显示集合线程不安全的示例
        showCollectionThreadUnsafe();
    }
    private static void showCollectionThreadUnsafe() {
        for (int j=0;j<3;j++) {
            new Thread(()->{
                for (int i = 0; i < 50; i++) {
                    CollectionDemo.list.add(i);
                }
            }).start();
        }
        while (Thread.activeCount()>2){}
        System.out.println(list.toString());
    }
}
```

**运行结果**

这个时候出现了如下错误：

![img_18.png](Image2%2Fimg_18.png)

**为什么会出现这个错误**

定位到错误的:

`at java.util.ArrayList.add(ArrayList.java:465)`

**查看源码**

![img_19.png](Image2%2Fimg_19.png)

**错误原因**

在ArrayList中，add方法是线程不安全的，当多个线程同时调用add方法时，就会出现并发修改异常。

**解决方法**

**1. 使用Collections.synchronizedList方法将ArrayList包装为线程安全的List**

> List<String> list = Collections.synchronizedList(new ArrayList<>());

**2. 使用CopyOnWriteArrayList来实现线程安全的List**

> CopyOnWriteArrayList 是 Java 并发包 java.util.concurrent 中提供的并发容器，本质上是一个线程安全且读操作无锁的 ArrayList。
> 它在确保线程安全的前提下，通过牺牲写操作的效率来保证读操作的高效。 
> 所谓 CopyOnWrite 就是通过复制的方式来完成对数据的修改，在修改时复制一个新的数组，在上面进行修改，
> 不会对旧的数组进行改变，也就没有读写数据不一致的问题了。

源码

```java
    public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
```

优点

- 读操作性能高
- 迭代器遍历不会抛出异常

缺点

- 内存占用大
- 无法保证实时性

![img_21.png](Image2%2Fimg_21.png)

**3. 使用Vector来实现线程安全的List,其add的方法添加`synchronized`关键字**

![img_20.png](Image2%2Fimg_20.png)


#### 附加

**HashSet线程不安全**

```java
import java.util.HashSet;
public class HashSetDemo {
    private static HashSet<Integer> set = new HashSet<>();
    public static void main(String[] args) {
        //显示集合线程不安全的示例
        showHashSetThreadUnsafe();
    }
    private static void showHashSetThreadUnsafe() {
        for (int j=0;j<3;j++) {
            new Thread(()->{
                for (int i = 0; i < 50; i++) {}
            }
        }
    }
}
```

**HashMap线程不安全**

```java
import java.util.*;
public class HashSetDemo {
    private static HashSet<Integer> set = new HashSet<>();
    public static void main(String[] args) {
        // 显示HashMap线程不安全的示例
        showHashMapThreadUnsafe();
    }
    /**
     * 举例展示hashMap线程不安全的示例
     */
    private static void showHashMapThreadUnsafe() {
        for (int j=0;j<10;j++) {
            int finalJ = j+1;
            new Thread(()->{
                for (int i = 0; i < 200; i++) {
                    CollectionDemo.map.put(String.valueOf(i*finalJ),i);
                }
            }).start();
        }
        while (Thread.activeCount()>2){}
        System.out.println(CollectionDemo.map.size());
    }
}
```

#### HashMap/HashSet线程不安全的解决方法

- 1、使用Collections.synchronizedMap(new HashMap<>());

- 2、使用 ConcurrentHashMap

> Map<String, String> map = new ConcurrentHashMap<>();
