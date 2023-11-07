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

