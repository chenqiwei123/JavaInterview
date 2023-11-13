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


