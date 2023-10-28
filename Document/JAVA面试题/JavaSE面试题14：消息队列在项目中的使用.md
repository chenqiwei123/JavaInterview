### 消息队列在项目中的使用
**消息队列的特点**

> 由于在高并发的环境下，来不及同步处理用户发送的请求，则会导致请求发生阻塞，比如说，大量的 insert，update 之类的请求同时到达数据库 MySQL, 直接导致无数的行锁表锁，甚至会导致请求堆积过多，从而触发 too many connections ( 链接数太多 ) 错误，使用消息队列可以解决 【异步通信】

**1、异步**
![img_19.png](Image%2Fimg_19.png)
**2、并行**
![img_20.png](Image%2Fimg_20.png)
**3、排队**
![img_21.png](Image%2Fimg_21.png)

**消息队列在电商中的使用场景**

![img_22.png](Image%2Fimg_22.png)
