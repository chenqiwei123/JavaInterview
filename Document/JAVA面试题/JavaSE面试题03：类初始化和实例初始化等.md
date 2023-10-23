### 类初始化实例初始化

#### 类初始化
> 一个类要创建实例需要先加载并初始化该类
>
> main方法所在的类需要先加载和初始化
>
> 一个子类要初始化需要先初始化父类
>
> 一个类初始化就是执行 clinit 方法
>
> clinit 方法由`静态类变量显示赋值代码`和`静态代码块`组成
>
> 类变量显示赋值代码和静态代码块代码`从上到下执行`
>
> clinit 方法只调用一次

#### 实例初始化过程

> 实例初始化就是执行 init() 方法
> 
> init () 方法可能重载有多个，有几个构造器就有几个 init() 方法
> 
> init() 方法由非静态实例变量显示赋值代码和非静态代码块，对应构造器代码组成
> 
> 非静态实例变量显示赋值代码和非静态代码块`从上到下顺序执行`，而对应构造器的代码最后执行
> 
> 每次创建实例对象，调用对应构造器，执行的就是对应的 ini方法
> 
> init 方法的首行是super()和super(实参列表) ,即对应父类的 init 方法

[Parent.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion01%2FParent.java)

```java

@Data
public class Parent {
    private String name=getNameInfo();
    private static String age=getAgeInfo();
    static {
        System.out.println(1);
    }
    {
        System.out.println(2);
    }
    public Parent(){
        System.out.println(3);
    }
    public String getNameInfo(){
        System.out.println(4);
        return "4";
    }
    public static String getAgeInfo(){
        System.out.println(5);
        return "5";
    }
}

```

[Son.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion01%2FSon.java)

```java

@Data
public class Son extends Parent{
    private String sex=getNameInfo();
    private static String num=getAgeInfo();
    static {
        System.out.println(6);
    }
    {
        System.out.println(7);
    }
    public Son(){
        System.out.println(8);
    }
    public String getNameInfo(){
        System.out.println(9);
        return "9";
    }
    public static String getAgeInfo(){
        System.out.println(10);
        return "10";
    }

}

```

[TestMain.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fjavainterview%2FVersion01%2FTestMain.java)

```java
public class TestMain {
    public static void main(String[] args) {
        Son son=new Son(); // 预计5,1,10,6,9,2,3,9,7,8 正确
        System.out.println();
        Son son1=new Son();// 预计 9,2,3,9,7,8 正确
    }
}

```
