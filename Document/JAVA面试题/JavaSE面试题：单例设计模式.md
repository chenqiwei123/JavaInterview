### 单例模式
#### 1.什么事Singleton?
> Singleton:在Java中 即指单例设置模式，探视软件开发最常用的设置模式之一

通俗解释：单例模式
> 单：唯一
> 
> 例：实例
> 
> 单例设计模式，即某个类在整个系统中只能有一个实例对象可被获取和使用的代码模式
> 
> 例如：代表JVM运行环境的Runtime类

**要点：**
> 
> **一是某个类只能有一个实例**
> 
> ​ 构造器私有化

> **二是他必须自行创建实例**
> 
> ​ 含有一个该类的静态变量来保存这个唯一的实例

> **三是它必须自行向整个系统提供这个实例**
> 
> ​ 对外提供获取该类实例对象的方式
>

#### 几种常见形式

**饿汉式**:
> 直接创建对象，不存在线程安全问题
> 直接实例化饿汉式(简洁直观)
> 
> 枚举式 (最简洁)
> 
> 静态代码块饿汉式(适合复杂实例化)

**懒汉式:延迟创建对象**
> 线程不安全(使用于单线程)
>
> 线程安全(使用于多线程)
>
> 静态内部类模式 (适用于多线程)

#### 代码实现如下
[JavaInterviewQuestion.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FJavaInterviewQuestion.java)

```java

@Data
public class JavaInterviewQuestion {
    private String properties;
    public static final JavaInterviewQuestion INSTANCE_HUNGRY; // 饿汉式
    static {
        INSTANCE_HUNGRY=new JavaInterviewQuestion();
        Properties proper=new Properties();
        try {
            // 饿汉式通常会出现一些需要读取properties文件一些定义的变量，例如sql的jdbc
            proper.load(JavaInterviewQuestion.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        INSTANCE_HUNGRY.setProperties((String) proper.get("cqw"));
    }

    public static JavaInterviewQuestion INSTANCE_LAZY; // 懒汉式
    public JavaInterviewQuestion getInstanceLazyDemo(){
        if (INSTANCE_LAZY == null ) {
            synchronized (INSTANCE_LAZY) { // 防止初始化实例变量时候，多线程创建多个实例不一致的情况。
                if (INSTANCE_LAZY == null){
                    return INSTANCE_LAZY=new JavaInterviewQuestion();
                }
            }
        }
        return INSTANCE_LAZY;
    }
}
```
