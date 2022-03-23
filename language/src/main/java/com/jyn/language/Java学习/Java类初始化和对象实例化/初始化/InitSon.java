package com.jyn.language.Java学习.Java类初始化和对象实例化.初始化;

/**
 * 类初始化过程：(父->子 & 静态变量->静态代码)
 * 父类<clinit>方法 -> 初始化父类静态变量 -> 执行父类静态代码块
 * -> 执行子类本身<clinit>方法 -> 初始化子类本身静态变量 -> 执行子类本身静态代码块
 */
public class InitSon extends InitFather {

    private static int j = staticFun();

    private InnerClass innerClass; //静态内部类并不会主动初始化

    public static int staticFun() {
        System.out.println("InitSon 静态变量所引用的static方法");
        return 1;
    }

    /*
     * 在static语句块中使用到静态变量时一定要将该静态变量的声明语句放在static语句块的前面,
     * 否则会发生illegal forward references的编译错误
     */
    static {
        System.out.println("InitSon static代码块");
    }

    /**
     * 构造方法默认第一句是 super ，所以会先执行父类的<init>()
     */
    InitSon() {
        System.out.println("InitSon 构造方法");
    }

    {
        System.out.println("InitSon 普通代码块");
    }

    public static class InnerClass {
        static {
            System.out.println("InnerClass static代码块");
        }

        InnerClass() {
            System.out.println("InnerClass 构造方法");
        }

        {
            System.out.println("InnerClass 普通代码块");
        }
    }

    public static void main(String[] args) {

    }
}
